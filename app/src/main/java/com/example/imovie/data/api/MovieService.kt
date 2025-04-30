package com.example.imovie.data.api

import com.example.imovie.data.model.Movie
import retrofit2.http.GET

interface MovieService {

    @GET("shows?page=1")
    suspend fun getMovies(): List<Movie>
}