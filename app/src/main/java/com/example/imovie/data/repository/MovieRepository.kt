package com.example.imovie.data.repository

import com.example.imovie.data.model.Movie
import com.example.imovie.data.source.local.LocalMovieDataSource
import com.example.imovie.data.source.remote.RemoteMovieDataSource
import timber.log.Timber
import javax.inject.Inject

class MovieRepository @Inject constructor(
    private val localMovieDataSource: LocalMovieDataSource,
    private val remoteMovieDataSource: RemoteMovieDataSource
) {

    suspend fun getMovies(): Result<List<Movie>> {
        return try {
            val movies = remoteMovieDataSource.getMovies()

            // Persist movies to local db
            localMovieDataSource.clearMovies()
            localMovieDataSource.saveMovies(movies)

            Result.success(movies)
        } catch (e: Exception) {
            Timber.e(e, "Failed to fetch movies: ${e.localizedMessage}")
            Timber.d("Loading from cache")

            val cachedMovies = localMovieDataSource.getAllMovies()

            if (cachedMovies.isNotEmpty()) {
                Result.success(cachedMovies)
            } else {
                Result.failure(e)
            }
        }
    }
}