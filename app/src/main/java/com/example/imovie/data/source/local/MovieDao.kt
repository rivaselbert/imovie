package com.example.imovie.data.source.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.imovie.data.model.Movie

@Dao
interface MovieDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMovies(movies: List<Movie>)

    @Query("SELECT * FROM movies")
    suspend fun getAllMovies(): List<Movie>

    @Query("DELETE FROM movies")
    suspend fun clearAllMovies()
}