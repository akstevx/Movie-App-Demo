package com.example.moviesappdemo.datasource.data

import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import androidx.paging.PagedList
import com.example.moviesappdemo.datasource.local.dao.LatestMoviesDao
import com.example.moviesappdemo.datasource.local.dao.PopularMoviesDao
import com.example.moviesappdemo.datasource.local.dao.UpcomingMoviesDao
import com.example.moviesappdemo.model.MovieModel
import com.example.moviesappdemo.model.Query
import kotlinx.coroutines.CoroutineScope
import javax.inject.Inject


class MoviesPageDataSourceFactory @Inject constructor(
    private val query: Query,
    private val scope: CoroutineScope,
    private val remoteDataSource: MovieRemoteDataSource,
    private val latestMoviesDao: LatestMoviesDao,
    private val upcomingMoviesDao: UpcomingMoviesDao,
    private val popularMoviesDao: PopularMoviesDao
) : DataSource.Factory<Int, MovieModel>() {

    val movieLiveData = MutableLiveData<MoviesPageDataSource>()

    override fun create(): DataSource<Int, MovieModel> {
        val source = MoviesPageDataSource(
            query = query,
            coroutineScope = scope,
            latestMoviesDao = latestMoviesDao,
            upcomingMoviesDao = upcomingMoviesDao,
            popularMoviesDao = popularMoviesDao,
            remoteDataSource = remoteDataSource
        )
        movieLiveData.postValue(source)
        return source
    }

    companion object {
        private const val PAGE_SIZE = 10
        fun pagedListConfig() = PagedList.Config.Builder()
            .setInitialLoadSizeHint(PAGE_SIZE)
            .setPageSize(PAGE_SIZE)
            .setEnablePlaceholders(true)
            .build()
    }

}
