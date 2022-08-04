package ru.fbear.tmdbviewer.ui.home

import android.content.res.Configuration
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import ru.fbear.tmdbviewer.Type
import ru.fbear.tmdbviewer.ui.theme.TMDBviewerTheme

@Composable
fun Home(navController: NavController, viewModel: HomeViewModel) {
    val tabs = listOf(
        TabItem.Movies,
        TabItem.TV
    )
    var selectedTab by remember { mutableStateOf<TabItem>(TabItem.Movies) }
    val popularMovies by viewModel.popularMovies.collectAsState(emptyList())
    val popularTVs by viewModel.popularTVs.collectAsState(emptyList())

    Scaffold(
        topBar = {
            HomeGridTabs(
                tabs = tabs,
                selectedTab = selectedTab,
                onSelectedTabChange = { selectedTab = it })
        }
    ) { innerPadding ->
        Box(modifier = Modifier.padding(innerPadding)) {
            HomeContent(
                selectedTab = selectedTab,
                popularMovies = popularMovies,
                popularTVs = popularTVs,
                onLoadMoreMovies = { viewModel.getPopularMovies() },
                onLoadMoreTV = { viewModel.getPopularTVs() },
            ) {
                when (selectedTab) {
                    TabItem.Movies -> {
                        navController.navigate("detail/${Type.Movie.string}/${it.id}")
                    }
                    TabItem.TV -> {
                        navController.navigate("detail/${Type.TV.string}/${it.id}")
                    }
                }
            }
        }
    }
}

@Preview(
    name = "dark theme",
    group = "themes",
    uiMode = Configuration.UI_MODE_NIGHT_YES,
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
fun HomePreview() {
    TMDBviewerTheme {
        Home(rememberNavController(), viewModel())
    }
}