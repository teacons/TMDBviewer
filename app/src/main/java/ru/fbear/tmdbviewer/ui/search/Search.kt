package ru.fbear.tmdbviewer.ui.search

import android.content.res.Configuration.UI_MODE_NIGHT_NO
import android.content.res.Configuration.UI_MODE_NIGHT_YES
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import ru.fbear.tmdbviewer.R
import ru.fbear.tmdbviewer.Type
import ru.fbear.tmdbviewer.model.SearchListEntry
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

    val isLogined by profileViewModel.isLogined.collectAsState()

    Search(
        searchTag = searchTag,
        searchedMovies = searchedMovies,
        searchedTV = searchedTV,
        searchedMoviesTotalResult = searchedMoviesTotalResult,
        searchedTVTotalResult = searchedTVTotalResult,
        isLogined = isLogined,
        isLiked = { id, type -> profileViewModel.isFavorite(id, type) },
        onSearchTagUpdate = { tag -> searchViewModel.updateSearchTag(tag) },
        onLikeChange = { liked, id, type -> profileViewModel.markAsFavorite(liked, id, type) },
        onShowAll = { type -> navController.navigate("search/more/${type.string}") },
        onItemClick = { id, type -> navController.navigate("detail/${type.string}/$id") }
    )

}

@Composable
private fun Search(
    searchTag: String,
    searchedMovies: List<SearchListEntry>,
    searchedTV: List<SearchListEntry>,
    searchedMoviesTotalResult: Int,
    searchedTVTotalResult: Int,
    isLogined: Boolean,
    isLiked: (Int, Type) -> Boolean,
    onSearchTagUpdate: (String) -> Unit,
    onLikeChange: suspend (Boolean, Int, Type) -> Unit,
    onShowAll: (Type) -> Unit,
    onItemClick: (Int, Type) -> Unit
) {

    val scaffoldState = rememberScaffoldState()
    val context = LocalContext.current

    Scaffold(
        scaffoldState = scaffoldState,
        topBar = { SearchBar(value = searchTag, onValueChange = onSearchTagUpdate) }
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
                    isLiked = isLiked,
                    onLikedChange = { liked, id, type ->
                        if (isLogined) onLikeChange(liked, id, type)
                        else scaffoldState.snackbarHostState.showSnackbar(context.getString(R.string.need_login))
                    },
                    onShowAll = onShowAll,
                    onItemClick = onItemClick
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
    uiMode = UI_MODE_NIGHT_YES
)
@Preview(
    name = "day theme",
    group = "themes",
    uiMode = UI_MODE_NIGHT_NO
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
        Search(
            searchTag = "Thor: Love and Thunder",
            searchedMovies = List(15) {
                object : SearchListEntry {
                    override val id = it
                    override val posterPath: String? = null
                    override val name = "Thor: Love and Thunder"
                    override val voteAverage = 2 / 3F * it
                }
            },
            searchedTV = List(15) {
                object : SearchListEntry {
                    override val id = it
                    override val posterPath: String? = null
                    override val name = "Thor: Love and Thunder"
                    override val voteAverage = 2 / 3F * it
                }
            },
            searchedMoviesTotalResult = 15,
            searchedTVTotalResult = 15,
            isLogined = true,
            isLiked = { id, _ -> id % 2 == 0 },
            onSearchTagUpdate = {},
            onLikeChange = { _, _, _ -> },
            onShowAll = {},
            onItemClick = { _, _ -> }
        )
    }
}