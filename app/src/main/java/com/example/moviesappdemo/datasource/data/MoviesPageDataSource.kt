package com.example.moviesappdemo.datasource.data

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.paging.PageKeyedDataSource
import com.example.moviesappdemo.datasource.local.dao.LatestMoviesDao
import com.example.moviesappdemo.datasource.local.dao.PopularMoviesDao
import com.example.moviesappdemo.datasource.local.dao.UpcomingMoviesDao
import com.example.moviesappdemo.datasource.network.api.NetworkState
import com.example.moviesappdemo.datasource.network.api.UseCaseResult
import com.example.moviesappdemo.model.MovieModel
import com.example.moviesappdemo.model.Query
import kotlinx.coroutines.*
import javax.inject.Inject

class MoviesPageDataSource @Inject constructor(
    private val remoteDataSource: MovieRemoteDataSource,
    private val query: Query,
    private val coroutineScope: CoroutineScope,
    private val latestMoviesDao: LatestMoviesDao,
    private val upcomingMoviesDao: UpcomingMoviesDao,
    private val popularMoviesDao: PopularMoviesDao
) : PageKeyedDataSource<Int, MovieModel>() {

    val networkState = MutableLiveData<NetworkState>()

    override fun loadInitial(
        params: LoadInitialParams<Int>,
        callback: LoadInitialCallback<Int, MovieModel>
    ) {
        networkState.postValue(NetworkState.IS_LOADING)
        when (query) {
            Query.POPULAR_MOVIES -> {
                fetchPopularMovies(page = 1) {
                    callback.onResult(it, null, 2)
                }
            }

            Query.LATEST_MOVIES -> {
                fetchLatestMovies(page = 1) {
                    callback.onResult(it, null, 2)
                }
            }
            Query.UPCOMING_MOVIES -> {
                fetchUpcomingMovies(page = 1) {
                    callback.onResult(it, null, 2)
                }
            }
        }

    }

    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, MovieModel>) {
        networkState.postValue(NetworkState.IS_LOADING)
        val page = params.key
        when (query) {
            Query.POPULAR_MOVIES -> {
                fetchPopularMovies(page = page) {
                    callback.onResult(it, page + 1)
                }
            }
            Query.LATEST_MOVIES -> {
                fetchLatestMovies(page = page) {
                    callback.onResult(it, page + 1)
                }
            }
            Query.UPCOMING_MOVIES -> {
                fetchUpcomingMovies(page = page) {
                    callback.onResult(it, page + 1)
                }
            }
        }

    }

    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, MovieModel>) {
        val page = params.key
        when (query) {
            Query.POPULAR_MOVIES -> {
                fetchPopularMovies(page = page) {
                    callback.onResult(it, page - 1)
                }
            }
            Query.LATEST_MOVIES -> {
                fetchLatestMovies(page = page) {
                    callback.onResult(it, page - 1)
                }
            }
            Query.UPCOMING_MOVIES -> {
                fetchUpcomingMovies(page = page) {
                    callback.onResult(it, page - 1)
                }
            }
        }
    }


    private fun fetchPopularMovies(page: Int, callback: (List<MovieModel>) -> Unit) {
        coroutineScope.launch(getJobErrorHandler()) {
            when (val response = remoteDataSource.getPopularMovies(page)) {
                is UseCaseResult.Error -> {
                    networkState.postValue(NetworkState.ERROR(response.message ?: "Unknown error"))
                    postError(response.message)
                }
                is UseCaseResult.Success -> {
                    val results = response.data.results
                    popularMoviesDao.insertAll(results)
                    callback(results)
                    networkState.postValue(NetworkState.HAS_LOADED)
                }
            }
        }
    }

    private fun fetchLatestMovies(page: Int, callback: (List<MovieModel>) -> Unit) {
        println("FETCHING LASTEST MOVIES ")
        coroutineScope.launch(getJobErrorHandler()) {
            when (val response = remoteDataSource.getLatestMovies(page)) {
                is UseCaseResult.Error -> {
                    networkState.postValue(NetworkState.ERROR(response.message ?: "Unknown error"))
                    postError(response.message)
                }
                is UseCaseResult.Success -> {
                    val results = response.data.results
                    latestMoviesDao.insertAll(results)
                    callback(results)
                    networkState.postValue(NetworkState.HAS_LOADED)
                    println("NETWORK UPDATE POSTED ${networkState.value}")
                }
            }
        }
    }

    private fun fetchUpcomingMovies(page: Int, callback: (List<MovieModel>) -> Unit) {

        coroutineScope.launch(getJobErrorHandler()) {
            when (val response = remoteDataSource.getUpcomingMovies(page)) {
                is UseCaseResult.Error -> {
                    networkState.postValue(NetworkState.ERROR(response.message ?: "Unknown error"))
                    postError(response.message)
                }
                is UseCaseResult.Success -> {
                    val results = response.data.results
                    upcomingMoviesDao.insertAll(results)
                    callback(results)
                    networkState.postValue(NetworkState.HAS_LOADED)
                }
            }
        }
    }

    private fun getJobErrorHandler() = CoroutineExceptionHandler { _, e ->
        postError(e.message ?: e.toString())
    }

    private fun postError(message: String?) {
        Log.e("NewsPageDataSource", "An error happened: $message")
    }
}
