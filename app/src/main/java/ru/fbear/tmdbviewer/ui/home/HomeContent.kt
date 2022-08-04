package ru.fbear.tmdbviewer.ui.home

import androidx.compose.runtime.Composable
import ru.fbear.tmdbviewer.HomeGridEntry
import ru.fbear.tmdbviewer.model.MovieListResultObject
import ru.fbear.tmdbviewer.model.TVListResultObject

@Composable
fun HomeContent(
    selectedTab: TabItem,
    popularMovies: List<MovieListResultObject>,
    popularTVs: List<TVListResultObject>,
    onLoadMoreMovies: () -> Unit,
    onLoadMoreTV: () -> Unit,
    onItemClick: (HomeGridEntry) -> Unit
) {

    when (selectedTab) {
        is TabItem.Movies -> HomeContentGrid(
            listItems = popularMovies,
            onLoadMore = onLoadMoreMovies,
            onItemClick = onItemClick
        )
        is TabItem.TV -> HomeContentGrid(
            listItems = popularTVs,
            onLoadMore = onLoadMoreTV,
            onItemClick = onItemClick
        )
    }
}