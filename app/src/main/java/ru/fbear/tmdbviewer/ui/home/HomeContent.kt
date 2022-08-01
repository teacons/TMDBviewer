package ru.fbear.tmdbviewer.ui.home

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.lifecycle.viewmodel.compose.viewModel
import ru.fbear.tmdbviewer.TMDBViewModel

@Composable
fun HomeContent(selectedTab: TabItem, viewModel: TMDBViewModel = viewModel()) {
    val popularMovies by viewModel.popularMovies.collectAsState(emptyList())
    val popularTVs by viewModel.popularTVs.collectAsState(emptyList())

    when (selectedTab) {
        is TabItem.Movies -> HomeContentGrid(popularMovies) { viewModel.getPopularMovies() }
        is TabItem.TV -> HomeContentGrid(popularTVs) { viewModel.getPopularTVs() }
    }

}