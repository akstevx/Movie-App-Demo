package com.example.moviesappdemo.datasource.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.moviesappdemo.datasource.local.dao.LatestMoviesDao
import com.example.moviesappdemo.datasource.local.dao.PopularMoviesDao
import com.example.moviesappdemo.datasource.local.dao.UpcomingMoviesDao
import com.example.moviesappdemo.model.MovieModel
import com.example.moviesappdemo.model.ResponseModel

@Database(entities = [MovieModel::class], version = 4, exportSchema = false)
@TypeConverters(MovieConverter::class)

abstract class MovieAppDatabase: RoomDatabase() {
    abstract fun latestMoviesDao(): LatestMoviesDao
    abstract fun popularMoviesDao(): PopularMoviesDao
    abstract fun upcomingMoviesDao(): UpcomingMoviesDao
}