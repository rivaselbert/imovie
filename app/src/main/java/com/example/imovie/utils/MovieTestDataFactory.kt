package com.example.imovie.utils

import com.example.imovie.data.model.Image
import com.example.imovie.data.model.Movie
import com.example.imovie.data.model.Rating

object MovieTestDataFactory {

    val movie = Movie(
        id = 250,
        url = "https://www.tvmaze.com/shows/250/kirby-buckets",
        name = "Kirby Buckets",
        type = "Scripted",
        language = "English",
        genres = listOf("Comedy"),
        rating = Rating(average = null),
        image = Image(
            medium = "https://static.tvmaze.com/uploads/images/medium_portrait/1/4600.jpg",
            original = "https://static.tvmaze.com/uploads/images/original_untouched/1/4600.jpg"
        ),
        summary = "The single-camera series that mixes live-action and animation stars Jacob Bertrand as the title character. " +
                "Kirby Buckets introduces viewers to the vivid imagination of charismatic 13-year-old Kirby Buckets, who dreams of becoming a famous animator like his idol, Mac MacCallister..."
    )

    val allMovies = listOf(movie)
}