package ru.fbear.tmdbviewer.ui.search

import android.content.res.Configuration.UI_MODE_NIGHT_NO
import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Divider
import androidx.compose.material.LocalContentColor
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ru.fbear.tmdbviewer.Type
import ru.fbear.tmdbviewer.model.SearchListEntry
import ru.fbear.tmdbviewer.ui.theme.TMDBviewerTheme

@Composable
fun SearchList(
    searchedMovies: List<SearchListEntry>,
    searchedTV: List<SearchListEntry>,
    totalMoviesResults: Int,
    totalTVResults: Int,
    isLiked: (Int, Type) -> Boolean,
    onLikedChange: suspend (Boolean, Int, Type) -> Unit,
    onShowAll: (Type) -> Unit,
    onItemClick: (Int, Type) -> Unit
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(8.dp),
        modifier = Modifier
            .verticalScroll(rememberScrollState())
            .padding(8.dp)
    ) {
        if (searchedMovies.isNotEmpty()) {
            SearchSubList(
                title = stringResource(id = Type.Movie.title),
                items = searchedMovies,
                totalResults = totalMoviesResults,
                isLiked = { isLiked(it, Type.Movie) },
                onLikedChange = { liked, id -> onLikedChange(liked, id, Type.Movie) },
                onShowAllClick = { onShowAll(Type.Movie) },
                onItemClick = { id -> onItemClick(id, Type.Movie) }
            )
            if (searchedTV.isNotEmpty()) Divider()
        }
        if (searchedTV.isNotEmpty()) {
            SearchSubList(
                title = stringResource(id = Type.TV.title),
                items = searchedTV,
                totalResults = totalTVResults,
                isLiked = { isLiked(it, Type.TV) },
                onLikedChange = { liked, id -> onLikedChange(liked, id, Type.TV) },
                onShowAllClick = { onShowAll(Type.TV) },
                onItemClick = { id -> onItemClick(id, Type.TV) }
            )
        }
    }
}


@Preview(
    name = "dark theme",
    group = "themes",
    uiMode = UI_MODE_NIGHT_YES
)
@Preview(
    name = "day theme",
    group = "themes",
    uiMode = UI_MODE_NIGHT_NO,
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
        CompositionLocalProvider(LocalContentColor provides MaterialTheme.colors.onBackground) {
            Box(modifier = Modifier.background(MaterialTheme.colors.background)) {
                SearchList(
                    searchedMovies = filmItems,
                    searchedTV = tvItems,
                    totalMoviesResults = 15,
                    totalTVResults = 15,
                    isLiked = { id, _ -> id % 2 == 0 },
                    onLikedChange = { _, _, _ -> },
                    onShowAll = {},
                    onItemClick = { _, _ -> }
                )
            }
        }
    }
}