package ru.fbear.tmdbviewer.ui.search

import android.content.res.Configuration
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Divider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ru.fbear.tmdbviewer.R
import ru.fbear.tmdbviewer.SearchListEntry
import ru.fbear.tmdbviewer.ui.theme.TMDBviewerTheme

@Composable
fun SearchList(
    searchedMovies: List<SearchListEntry>,
    searchedTV: List<SearchListEntry>,
    totalMoviesResults: Int,
    totalTVResults: Int,
    onShowAllMovies: () -> Unit,
    onShowAllTV: () -> Unit,
    onMovieItemClick: (Int) -> Unit,
    onTVItemClick: (Int) -> Unit
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(8.dp),
        modifier = Modifier
            .verticalScroll(rememberScrollState())
            .padding(8.dp)
    ) {
        if (searchedMovies.isNotEmpty()) {
            SearchSubList(
                title = stringResource(id = R.string.movies),
                items = searchedMovies,
                totalResults = totalMoviesResults,
                onShowAllClick = onShowAllMovies,
                onItemClick = onMovieItemClick
            )
            Divider()
        }
        if (searchedTV.isNotEmpty()) {
            SearchSubList(
                title = stringResource(id = R.string.tv),
                items = searchedTV,
                totalResults = totalTVResults,
                onShowAllClick = onShowAllTV,
                onItemClick = onTVItemClick
            )
        }
    }
}

@Preview(
    name = "dark theme",
    group = "themes",
    uiMode = Configuration.UI_MODE_NIGHT_YES
)
@Preview(
    name = "ru lang",
    group = "language",
    locale = "RU"
)
@Preview(
    name = "en lang",
    group = "language",
    locale = "EN"
)
@Preview(
    name = "small font",
    group = "font scales",
    fontScale = 0.5f
)
@Preview(
    name = "large font",
    group = "font scales",
    fontScale = 1.5f
)
@Composable
fun SearchListPreview() {
    val filmItems = List(15) {
        object : SearchListEntry {
            override val id = it
            override val posterPath: String? = null
            override val name = "Movie $it"
            override val voteAverage = it * 2 / 3F
        }
    }
    val tvItems = List(15) {
        object : SearchListEntry {
            override val id = it
            override val posterPath: String? = null
            override val name = "TV $it"
            override val voteAverage = it * 2 / 3F
        }
    }
    TMDBviewerTheme {
        SearchList(
            searchedMovies = filmItems,
            searchedTV = tvItems,
            totalMoviesResults = 15,
            totalTVResults = 15,
            onShowAllMovies = {},
            onShowAllTV = {},
            onMovieItemClick = {},
            onTVItemClick = {}
        )
    }
}