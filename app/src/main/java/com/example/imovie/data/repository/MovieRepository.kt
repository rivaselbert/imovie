package com.example.imovie.data.repository

import com.example.imovie.data.model.Movie
import com.example.imovie.data.source.local.LocalMovieDataSource
import com.example.imovie.data.source.remote.RemoteMovieDataSource
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

class MovieRepository @Inject constructor(
    private val localMovieDataSource: LocalMovieDataSource,
    private val remoteMovieDataSource: RemoteMovieDataSource
) {

    suspend fun getMovies(): Result<List<Movie>> {
        return try {
            val movies = remoteMovieDataSource.getMovies()

            CoroutineScope(Dispatchers.IO).launch {
                // Persist movies to local db
                if (movies.isNotEmpty()) {
                    localMovieDataSource.clearMovies()
                    localMovieDataSource.saveMovies(movies)
                }
            }

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

    suspend fun searchMovies(searchText: String): Result<List<Movie>> {
        return try {
            val response = remoteMovieDataSource.searchMovies(searchText)
            val movies = response.map { it.show }

            CoroutineScope(Dispatchers.IO).launch {
                if (movies.isNotEmpty()) {
                    // Persist movies result to local db
                    localMovieDataSource.clearMovies()
                    localMovieDataSource.saveMovies(movies)
                }
            }

            Result.success(movies)
        } catch (e: Exception) {
            Timber.e(e, "Failed to fetch movies: ${e.localizedMessage}")
            Result.failure(e)
        }
    }
}