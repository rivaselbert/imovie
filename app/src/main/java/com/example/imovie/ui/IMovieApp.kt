package com.example.imovie.ui

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.imovie.ui.movie.MovieListScreen
import com.example.imovie.ui.navigation.Route

@Composable
fun IMovieApp() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = Route.MOVIE_LIST
    ) {
        composable(Route.MOVIE_LIST) {
            MovieListScreen()
        }
    }
}