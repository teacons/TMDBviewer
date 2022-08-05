package ru.fbear.tmdbviewer.ui.home

import androidx.compose.runtime.Composable
import ru.fbear.tmdbviewer.Type
import ru.fbear.tmdbviewer.model.HomeGridEntry
import ru.fbear.tmdbviewer.model.MovieListResultObject
import ru.fbear.tmdbviewer.model.TVListResultObject

@Composable
fun HomeContent(
    selectedTab: TabItem,
    popularMovies: List<MovieListResultObject>,
    popularTVs: List<TVListResultObject>,
    isLiked: (Int, Type) -> Boolean,
    onLikedChange: suspend (Boolean, Int, Type) -> Unit,
    onLoadMoreMovies: () -> Unit,
    onLoadMoreTV: () -> Unit,
    onItemClick: (HomeGridEntry) -> Unit
) {

    when (selectedTab) {
        is TabItem.Movies -> HomeContentGrid(
            listItems = popularMovies,
            isLiked = { isLiked(it, Type.Movie) },
            onLikedChange = { liked, id ->
                onLikedChange(liked, id, Type.Movie)
            },
            onLoadMore = onLoadMoreMovies,
            onItemClick = onItemClick
        )
        is TabItem.TV -> HomeContentGrid(
            listItems = popularTVs,
            isLiked = { isLiked(it, Type.TV) },
            onLikedChange = { liked, id ->
                onLikedChange(liked, id, Type.TV)
            },
            onLoadMore = onLoadMoreTV,
            onItemClick = onItemClick
        )
    }
}