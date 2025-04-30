package com.example.imovie.data.source.remote

import com.example.imovie.data.api.MovieService
import javax.inject.Inject

class RemoteMovieDataSource @Inject constructor(
    private val movieService: MovieService,
) {

    suspend fun getMovies() = movieService.getMovies()

    suspend fun searchMovies(searchText: String) =
        movieService.searchMovies(searchText)
}