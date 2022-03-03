package com.example.moviesappdemo.views.fragments.movietrend.latestmovies

import com.example.moviesappdemo.datasource.data.Data
import com.example.moviesappdemo.datasource.repository.MovieRepository
import com.example.moviesappdemo.model.MovieModel
import com.example.moviesappdemo.model.Query
import com.example.moviesappdemo.views.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LatestMoviesViewModel @Inject constructor(
    private val repository: MovieRepository,
) : BaseViewModel() {
    private var latestMovies: Data<MovieModel>? = null


    fun fetchLatestMovies(connectivityAvailable: Boolean): Data<MovieModel>? {
        if (latestMovies == null) {
            latestMovies =
                repository.observePagedMovieList(connectivityAvailable, this, Query.LATEST_MOVIES)
        }
        return latestMovies
    }

    fun refresh() {
        launch {
            repository.refreshMovieList(Query.LATEST_MOVIES)
        }
    }

}