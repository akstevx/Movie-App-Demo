package com.example.moviesappdemo.views.fragments.movietrend.upcomingmovies

import com.example.moviesappdemo.datasource.data.Data
import com.example.moviesappdemo.datasource.repository.MovieRepository
import com.example.moviesappdemo.model.MovieModel
import com.example.moviesappdemo.model.Query
import com.example.moviesappdemo.views.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UpcomingMoviesViewModel @Inject constructor(
    private val repository: MovieRepository,
) : BaseViewModel() {
    private var upcomingMovies: Data<MovieModel>? = null


    fun fetchUpcomingMovies(connectivityAvailable: Boolean): Data<MovieModel>? {
        if (upcomingMovies == null) {
            upcomingMovies =
                repository.observePagedMovieList(connectivityAvailable, this, Query.UPCOMING_MOVIES)
        }
        return upcomingMovies
    }

    fun refresh() {
        launch {
            repository.refreshMovieList(Query.UPCOMING_MOVIES)
        }
    }
}