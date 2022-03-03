package com.example.moviesappdemo.views.fragments.movietrend.trendingmovies

import com.example.moviesappdemo.datasource.data.Data
import com.example.moviesappdemo.datasource.repository.MovieRepository
import com.example.moviesappdemo.model.MovieModel
import com.example.moviesappdemo.model.Query
import com.example.moviesappdemo.views.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TrendingMoviesViewModel @Inject constructor(
    private val repository: MovieRepository,
) : BaseViewModel() {
    private var popularMovies: Data<MovieModel>? = null


    fun fetchPopularMovies(connectivityAvailable: Boolean): Data<MovieModel>? {
        if (popularMovies == null) {
            popularMovies =
                repository.observePagedMovieList(connectivityAvailable, this, Query.POPULAR_MOVIES)
        }
        return popularMovies
    }


    fun refresh() {
        launch {
            repository.refreshMovieList(Query.LATEST_MOVIES)
        }
    }
}