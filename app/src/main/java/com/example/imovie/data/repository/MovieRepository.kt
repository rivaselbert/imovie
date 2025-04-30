package com.example.imovie.data.repository

import com.example.imovie.data.model.Movie
import com.example.imovie.data.source.remote.RemoteMovieDataSource
import timber.log.Timber
import javax.inject.Inject

class MovieRepository @Inject constructor(
    private val remoteMovieDataSource: RemoteMovieDataSource
) {

    suspend fun getMovies(): Result<List<Movie>> {
        return try {
            val result = remoteMovieDataSource.getMovies()
            Result.success(result)
        } catch (e: Exception) {
            Timber.e(e, "Failed to fetch movies: ${e.localizedMessage}")
            Result.failure(e)
        }
    }
}