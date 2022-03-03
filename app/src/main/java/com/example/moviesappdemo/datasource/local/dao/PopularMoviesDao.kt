package com.example.moviesappdemo.datasource.local.dao

import androidx.lifecycle.LiveData
import androidx.paging.DataSource
import androidx.room.*
import com.example.moviesappdemo.model.MovieModel

@Dao
interface PopularMoviesDao {
    @Query("Select * from Result")
    fun getPopularMovies(): LiveData<List<MovieModel>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(movieModelList: List<MovieModel>)

    @Query("SELECT * FROM Result")
    fun getPagedPopularMovies(): DataSource.Factory<Int, MovieModel>

    @Query("DELETE FROM Result")
    suspend fun deleteAllPopularMovies()

}