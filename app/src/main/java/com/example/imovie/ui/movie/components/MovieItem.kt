package com.example.imovie.ui.movie.components

import android.content.res.Configuration
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.imovie.R
import com.example.imovie.data.model.Movie
import com.example.imovie.ui.theme.IMovieTheme

@Composable
fun MovieItem(
    movie: Movie,
    onClick: (Movie) -> Unit,
) {
    Card(
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        ),
        onClick = { onClick(movie) }
    ) {
        Column(modifier = Modifier.fillMaxWidth()) {
            AsyncImage(
                modifier = Modifier
                    .fillMaxWidth()
                    .sizeIn(maxHeight = 350.dp)
                    .clip(RoundedCornerShape(8.dp)),
                model = movie.image?.medium,
                contentDescription = null,
                contentScale = ContentScale.Crop,
                error = painterResource(R.drawable.image_placeholder),
                placeholder = painterResource(R.drawable.image_placeholder)
            )
            Spacer(Modifier.height(12.dp))
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(
                    modifier = Modifier.weight(1f).padding(start = 4.dp),
                ) {
                    Text(
                        text = movie.name,
                        style = MaterialTheme.typography.bodyLarge.copy(
                            lineHeight = 18.sp
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
        }
    }
}

@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun MovieItemPreview() {
    IMovieTheme {
//        MovieItem(
//            movie = Movie()
//        )
    }
}