package com.example.moviesappdemo.viewmodel

import com.example.moviesappdemo.datasource.repository.MovieRepository
import com.example.moviesappdemo.model.Query
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.Mockito.*

@RunWith(JUnit4::class)
class MovieViewModelTest {

    private val coroutineScope = CoroutineScope(Dispatchers.IO)

    private val repository = mock(MovieRepository::class.java)

    @Test
    fun testNull() {

        verify(repository, never()).observePagedMovieList(false, coroutineScope, Query.LATEST_MOVIES)
        verify(repository, never()).observePagedMovieList(true, coroutineScope, Query.LATEST_MOVIES)
    }

    @Test
    fun doNotFetchWithoutObservers() {

        verify(repository, never()).observePagedMovieList(false, coroutineScope, Query.LATEST_MOVIES)
    }

    @Test
    fun doNotFetchWithoutObserverOnConnectionChange() {
        verify(repository, never()).observePagedMovieList(true, coroutineScope, Query.LATEST_MOVIES)
    }

}
