package com.example.moviesappdemo.datasource.repository.di

import com.example.moviesappdemo.datasource.data.MovieRemoteDataSource
import com.example.moviesappdemo.datasource.local.dao.LatestMoviesDao
import com.example.moviesappdemo.datasource.local.dao.PopularMoviesDao
import com.example.moviesappdemo.datasource.local.dao.UpcomingMoviesDao
import com.example.moviesappdemo.datasource.network.api.ApiService
import com.example.moviesappdemo.datasource.repository.MovieRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    @Singleton
    fun provideMovieRepository(
        remoteDataSource: MovieRemoteDataSource,
        latestMoviesDao: LatestMoviesDao,
        upcomingMoviesDao: UpcomingMoviesDao,
        popularMoviesDao: PopularMoviesDao
    ): MovieRepository = MovieRepository(
        remoteDataSource = remoteDataSource,
        latestMoviesDao = latestMoviesDao,
        popularMoviesDao = popularMoviesDao,
        upcomingMoviesDao = upcomingMoviesDao,
    )

}