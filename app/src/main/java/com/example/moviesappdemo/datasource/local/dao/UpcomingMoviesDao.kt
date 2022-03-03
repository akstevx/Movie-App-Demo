package com.example.moviesappdemo.datasource.local.dao

import androidx.lifecycle.LiveData
import androidx.paging.DataSource
import androidx.room.*
import com.example.moviesappdemo.model.MovieModel

@Dao
interface UpcomingMoviesDao {
    @Query("Select * from Result")
    fun getUpcomingMovies(): LiveData<List<MovieModel>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(movieModelList: List<MovieModel>)

    @Query("SELECT * FROM Result")
    fun getPagedUpcomingMovies(): DataSource.Factory<Int, MovieModel>

    @Query("DELETE FROM Result")
    suspend fun deleteAllUpcomingMovies()


}