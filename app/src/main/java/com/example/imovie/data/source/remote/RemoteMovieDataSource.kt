package com.example.imovie.data.source.remote

import com.example.imovie.data.api.MovieService
import javax.inject.Inject

class RemoteMovieDataSource @Inject constructor(
    private val movieService: MovieService,
) {

    // TODO: Add documentation
    suspend fun getMovies() = movieService.getMovies()
}