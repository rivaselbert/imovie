package com.example.imovie.data.model

data class Movie(
    val id: Int,
    val url: String,
    val name: String,
    val type: String,
    val language: String,
    val genres: List<String>,
    val rating: Rating,
    val image: Image?,
    val summary: String?,
)

data class Rating(
    val average: Double?
)

data class Image(
    val medium: String?,
    val original: String?
)