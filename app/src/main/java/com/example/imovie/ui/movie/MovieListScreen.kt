package com.example.imovie.ui.movie

import android.content.res.Configuration
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.imovie.ui.movie.components.MovieItem
import com.example.imovie.ui.movie.components.MovieListTopBar
import com.example.imovie.ui.theme.IMovieTheme

@Composable
fun MovieListScreen(
    viewModel: MovieViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    MovieListScreenContent(
        uiState = uiState
    )
}

@Composable
private fun MovieListScreenContent(
    uiState: MovieUIState,
) {
    Scaffold(
        topBar = {
            MovieListTopBar()
        },
        bottomBar = {
            NavigationBar(
                modifier = Modifier.fillMaxWidth(),
                containerColor = MaterialTheme.colorScheme.surface,
                tonalElevation = 0.5.dp
            ) {
                NavigationBarItem(
                    selected = false,
                    onClick = {},
                    icon = {
                        Icon(
                            imageVector = Icons.Default.Home,
                            contentDescription = null,
                        )
                    }
                )
            }
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(horizontal = 12.dp)
        ) {
            if (uiState.isLoading) {
                CircularProgressIndicator(
                    modifier = Modifier
                        .size(24.dp)
                        .align(Alignment.CenterHorizontally)
                )
            }

            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                items(uiState.movies) {movie ->
                    MovieItem(movie)
                }
            }
        }
    }
}

@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun MovieListScreenPreview() {
    IMovieTheme {
        MovieListScreenContent(
            uiState = MovieUIState()
        )
    }
}