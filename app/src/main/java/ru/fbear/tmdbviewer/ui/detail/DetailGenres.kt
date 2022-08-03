package ru.fbear.tmdbviewer.ui.detail

import android.content.res.Configuration
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.google.accompanist.flowlayout.FlowRow
import ru.fbear.tmdbviewer.Genre
import ru.fbear.tmdbviewer.R
import ru.fbear.tmdbviewer.ui.Chip
import ru.fbear.tmdbviewer.ui.theme.TMDBviewerTheme

@Composable
fun DetailGenres(genres: List<Genre>) {
    Column(
        verticalArrangement = Arrangement.spacedBy(8.dp),
        modifier = Modifier.padding(8.dp)
    ) {
        Text(
            text = stringResource(R.string.genres),
            style = MaterialTheme.typography.h6
        )
        FlowRow(
            crossAxisSpacing = 8.dp,
            mainAxisSpacing = 8.dp
        ) {
            genres.forEach { Chip(it.name) }
        }
    }
}

@Preview(
    name = "dark theme",
    group = "themes",
    uiMode = Configuration.UI_MODE_NIGHT_YES,
    showBackground = true
)
@Preview(
    name = "ru lang",
    group = "language",
    locale = "RU",
    showBackground = true
)
@Preview(
    name = "en lang",
    group = "language",
    locale = "EN",
    showBackground = true
)
@Preview(
    name = "small font",
    group = "font scales",
    fontScale = 0.5f,
    showBackground = true
)
@Preview(
    name = "large font",
    group = "font scales",
    fontScale = 1.5f,
    showBackground = true
)
@Composable
fun DetailGenresPreview() {
    TMDBviewerTheme {
        DetailGenres(
            genres = listOf(
                Genre(28, "Action"),
                Genre(12, "Adventure"),
                Genre(14, "Fantasy"),
                Genre(15, "Drama")
            )
        )
    }
}