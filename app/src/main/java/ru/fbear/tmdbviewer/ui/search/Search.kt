package ru.fbear.tmdbviewer.ui.search

import android.content.res.Configuration
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import ru.fbear.tmdbviewer.Type
import ru.fbear.tmdbviewer.ui.profile.ProfileViewModel
import ru.fbear.tmdbviewer.ui.theme.TMDBviewerTheme

@Composable
fun Search(
    navController: NavController,
    searchViewModel: SearchViewModel,
    profileViewModel: ProfileViewModel
) {
    val searchTag by searchViewModel.searchTag.collectAsState()

    val searchedMovies by searchViewModel.searchedMovies.collectAsState()
    val searchedMoviesTotalResult by searchViewModel.searchedMoviesTotalResults.collectAsState()
    val searchedTV by searchViewModel.searchedTVs.collectAsState()
    val searchedTVTotalResult by searchViewModel.searchedTVsTotalResults.collectAsState()

    Scaffold(
        topBar = { SearchBar(value = searchTag) { searchViewModel.updateSearchTag(it) } }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
        ) {
            if (searchedMovies.isNotEmpty() && searchedTV.isNotEmpty()) {
                SearchList(
                    searchedMovies = searchedMovies,
                    searchedTV = searchedTV,
                    totalMoviesResults = searchedMoviesTotalResult,
                    totalTVResults = searchedTVTotalResult,
                    isLiked = { id, type -> profileViewModel.isFavorite(id, type) },
                    onLikedChange = { liked, id, type ->
                        profileViewModel.markAsFavorite(
                            liked,
                            id,
                            type
                        )
                    },
                    onShowAllMovies = { navController.navigate("search/more/${Type.Movie.string}") },
                    onShowAllTV = { navController.navigate("search/more/${Type.TV.string}") },
                    onMovieItemClick = { navController.navigate("detail/${Type.Movie.string}/$it") },
                    onTVItemClick = { navController.navigate("detail/${Type.TV.string}/$it") }
                )
            } else {
                CompositionLocalProvider(LocalContentAlpha provides ContentAlpha.medium) {
                    Text(
                        text = "Нет результатов",
                        style = MaterialTheme.typography.h4,
                        modifier = Modifier.align(Alignment.Center)
                    )
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
fun SearchPreview() {
    TMDBviewerTheme {
        Search(rememberNavController(), viewModel(), viewModel())
    }
}