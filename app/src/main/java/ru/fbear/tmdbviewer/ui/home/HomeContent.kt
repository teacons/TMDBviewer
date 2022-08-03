package ru.fbear.tmdbviewer.ui.home

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.lifecycle.viewmodel.compose.viewModel
import ru.fbear.tmdbviewer.HomeGridEntry
import ru.fbear.tmdbviewer.TMDBViewModel

@Composable
fun HomeContent(
    selectedTab: TabItem,
    viewModel: TMDBViewModel = viewModel(),
    onItemClick: (HomeGridEntry) -> Unit
) {
    val popularMovies by viewModel.popularMovies.collectAsState(emptyList())
    val popularTVs by viewModel.popularTVs.collectAsState(emptyList())

    when (selectedTab) {
        is TabItem.Movies -> HomeContentGrid(
            listItems = popularMovies,
            onLoadMore = { viewModel.getPopularMovies() },
            onItemClick = onItemClick
        )
        is TabItem.TV -> HomeContentGrid(
            listItems = popularTVs,
            onLoadMore = { viewModel.getPopularTVs() },
            onItemClick = onItemClick
        )
    }
}