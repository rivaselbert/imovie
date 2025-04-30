package com.example.imovie.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.imovie.data.model.Movie
import com.example.imovie.ui.movie.MovieDetailsScreen
import com.example.imovie.ui.movie.MovieListScreen
import com.example.imovie.ui.navigation.NavigationActions
import com.example.imovie.ui.navigation.Route
import com.google.gson.Gson
import timber.log.Timber

@Composable
fun IMovieApp() {
    val navController = rememberNavController()
    val navigationActions = remember(navController) { NavigationActions(navController) }

    NavHost(
        navController = navController,
        startDestination = Route.MOVIE_LIST
    ) {
        composable(Route.MOVIE_LIST) {
            MovieListScreen(
                navigateToMovieDetails = { movie ->
                    navigationActions.navigateToMovieDetails(movie)
                }
            )
        }

        composable(Route.MOVIE_DETAILS) { backStackEntry ->
            val movieJson = backStackEntry.arguments?.getString("movie")

            val movie = if (movieJson != null) {
                try {
                    Gson().fromJson(movieJson, Movie::class.java)
                } catch (e: Exception) {
                    Timber.e(e, "Failed to parse movie JSON")
                    null
                }
            } else null

            movie?.let {
                MovieDetailsScreen(
                    movie = movie,
                    onNavigateBack = navController::popBackStack
                )
            }
        }
    }
}