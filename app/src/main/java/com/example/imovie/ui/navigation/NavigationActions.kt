package com.example.imovie.ui.navigation

import androidx.navigation.NavHostController
import com.example.imovie.data.model.Movie
import com.google.gson.Gson

class NavigationActions(private val navController: NavHostController) {
    fun navigateToMovieDetails(movie: Movie) {
        val movieJson = Gson().toJson(movie)

        navController.navigate(
            Route.MOVIE_DETAILS
                .replace("{movie}", movieJson)
        )
    }
}