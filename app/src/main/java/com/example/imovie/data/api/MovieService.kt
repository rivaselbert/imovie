package com.example.imovie.data.api

import com.example.imovie.data.model.Movie
import com.example.imovie.data.model.SearchMovieResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface MovieService {

    @GET("shows?page=1")
    suspend fun getMovies(): List<Movie>

    @GET("search/shows")
    suspend fun searchMovies(
        @Query("q") searchText: String
    ): List<SearchMovieResponse>
}