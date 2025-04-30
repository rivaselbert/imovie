package com.example.imovie.data.source.local

import com.example.imovie.data.model.Movie
import javax.inject.Inject

class LocalMovieDataSource @Inject constructor(
    private val movieDao: MovieDao,
) {

    suspend fun saveMovies(movies: List<Movie>) = movieDao.insertMovies(movies)

    suspend fun getAllMovies() = movieDao.getAllMovies()

    suspend fun clearMovies() = movieDao.clearAllMovies()
}