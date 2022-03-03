package com.example.moviesappdemo.datasource.repository

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.paging.DataSource
import androidx.paging.LivePagedListBuilder
import com.example.moviesappdemo.datasource.data.Data
import com.example.moviesappdemo.datasource.data.MovieRemoteDataSource
import com.example.moviesappdemo.datasource.data.MoviesPageDataSourceFactory
import com.example.moviesappdemo.datasource.local.dao.LatestMoviesDao
import com.example.moviesappdemo.datasource.local.dao.PopularMoviesDao
import com.example.moviesappdemo.datasource.local.dao.UpcomingMoviesDao
import com.example.moviesappdemo.datasource.network.api.NetworkState
import com.example.moviesappdemo.model.MovieModel
import com.example.moviesappdemo.model.Query
import kotlinx.coroutines.CoroutineScope
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors
import javax.inject.Inject

class MovieRepository @Inject constructor(
    val remoteDataSource: MovieRemoteDataSource,
    val latestMoviesDao: LatestMoviesDao,
    val upcomingMoviesDao: UpcomingMoviesDao,
    val popularMoviesDao: PopularMoviesDao

) {

    suspend fun refreshMovieList(query: Query) {
        when (query) {
            Query.POPULAR_MOVIES -> remoteDataSource.getPopularMovies(1)
            Query.LATEST_MOVIES -> remoteDataSource.getLatestMovies(1)
            Query.UPCOMING_MOVIES -> remoteDataSource.getUpcomingMovies(1)
        }

    }

    fun observePagedMovieList(
        connectivityAvailable: Boolean,
        coroutineScope: CoroutineScope,
        query: Query
    ): Data<MovieModel> {

        return if (connectivityAvailable)
            observePagedMovieFromApi(coroutineScope, query)
        else observeMovieFromLocal(query)
    }

    private fun observeMovieFromLocal(query: Query): Data<MovieModel> {
        val dataSourceFactory: DataSource.Factory<Int, MovieModel> = when (query) {
            Query.POPULAR_MOVIES -> popularMoviesDao.getPagedPopularMovies()
            Query.LATEST_MOVIES -> latestMoviesDao.getPagedLatestMovies()
            Query.UPCOMING_MOVIES -> upcomingMoviesDao.getPagedUpcomingMovies()
        }

        val networkStateObserver = MutableLiveData<NetworkState>()
        networkStateObserver.postValue(NetworkState.HAS_LOADED)

        return Data(
            LivePagedListBuilder(
                dataSourceFactory,
                MoviesPageDataSourceFactory.pagedListConfig()
            ).build(),
            networkStateObserver
        )
    }

    private fun observePagedMovieFromApi(
        ioCoroutineScope: CoroutineScope,
        query: Query
    ): Data<MovieModel> {
        val dataSourceFactory = MoviesPageDataSourceFactory(
            query = query,
            latestMoviesDao = latestMoviesDao,
            popularMoviesDao = popularMoviesDao,
            upcomingMoviesDao = upcomingMoviesDao,
            remoteDataSource = remoteDataSource,
            scope = ioCoroutineScope,
        )

        val networkState = Transformations.switchMap(dataSourceFactory.movieLiveData) {
            it.networkState
        }

        return Data(
            LivePagedListBuilder(
                dataSourceFactory,
                MoviesPageDataSourceFactory.pagedListConfig()
            ).setFetchExecutor(createFetchExecutor())
                .build(), networkState
        )
    }

    private fun createFetchExecutor(): ExecutorService {
        val threads = Runtime.getRuntime().availableProcessors() + 1
        return Executors.newFixedThreadPool(threads)
    }
}