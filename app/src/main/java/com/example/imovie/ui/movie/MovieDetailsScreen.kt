package com.example.imovie.ui.movie

import android.content.res.Configuration
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.FilledIconButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.imovie.R
import com.example.imovie.data.model.Movie
import com.example.imovie.ui.theme.IMovieTheme
import com.example.imovie.utils.MovieTestDataFactory
import com.example.imovie.utils.decodeHtml

@Composable
fun MovieDetailsScreen(
    movie: Movie,
    onNavigateBack: () -> Unit,
) {
    MovieDetailsScreenContent(
        movie = movie,
        onBackClick = onNavigateBack
    )
}

@Composable
fun MovieDetailsScreenContent(
    movie: Movie,
    onBackClick: () -> Unit,
) {
    Scaffold { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .verticalScroll(rememberScrollState())
        ) {
            Box {
                AsyncImage(
                    modifier = Modifier
                        .fillMaxWidth()
                        .sizeIn(maxHeight = 380.dp),
                    model = movie.image?.original,
                    contentDescription = null,
                    contentScale = ContentScale.FillWidth,
                    error = painterResource(R.drawable.image_placeholder),
                    placeholder = painterResource(R.drawable.image_placeholder)
                )
                TopBar(
                    onAddToFavoritesClick = {},
                    onBackClick = onBackClick
                )
            }
            Spacer(Modifier.height(16.dp))
            Row(
                modifier = Modifier.padding(horizontal = 16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(
                    modifier = Modifier.weight(1f),
                ) {
                    Text(
                        text = movie.name,
                        style = MaterialTheme.typography.bodyLarge.copy(
                            lineHeight = 18.sp,
                            fontWeight = FontWeight.Bold
                        ),
                        maxLines = 2
                    )
                    Text(
                        text = movie.genres.joinToString(", "),
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.outline,
                        maxLines = 1
                    )
                }
            }
            Spacer(Modifier.height(12.dp))
            Text(
                modifier = Modifier.padding(horizontal = 16.dp),
                text = movie.summary?.decodeHtml() ?: "",
                style = MaterialTheme.typography.bodyMedium
            )
        }
    }
}

@Composable
private fun TopBar(
    onAddToFavoritesClick: () -> Unit,
    onBackClick: () -> Unit,
) {
    var showOverflowMenu by remember { mutableStateOf(false) }

    Row(
        modifier = Modifier.padding(horizontal = 12.dp, vertical = 8.dp)
    ) {
        FilledIconButton(
            colors = IconButtonDefaults.filledIconButtonColors(
                containerColor = Color.Black.copy(alpha = 0.3f)
            ),
            onClick = onBackClick
        ) {
            Icon(
                imageVector = Icons.AutoMirrored.Filled.KeyboardArrowLeft,
                contentDescription = null
            )
        }
        Spacer(Modifier.weight(1f))
        FilledIconButton(
            colors = IconButtonDefaults.filledIconButtonColors(
                containerColor = Color.Black.copy(alpha = 0.3f)
            ),
            onClick = { showOverflowMenu = true }
        ) {
            Icon(
                painter = painterResource(R.drawable.ic_overflow_vert),
                contentDescription = null
            )
            DropdownMenu(
                expanded = showOverflowMenu,
                onDismissRequest = { showOverflowMenu = false }
            ) {
                DropdownMenuItem(
                    text = {
                        Text(text = stringResource(id = R.string.add_to_favorites))
                    },
                    onClick = {
                        onAddToFavoritesClick()
                        showOverflowMenu = false
                    }
                )
            }
        }
    }
}

@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun MovieDetailsScreenPreview() {
    IMovieTheme {
        MovieDetailsScreenContent(
            movie = MovieTestDataFactory.allMovies.first(),
            onBackClick = {},
        )
    }
}