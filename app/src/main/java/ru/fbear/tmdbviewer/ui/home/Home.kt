package ru.fbear.tmdbviewer.ui.home

import android.content.res.Configuration
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import ru.fbear.tmdbviewer.R
import ru.fbear.tmdbviewer.Type
import ru.fbear.tmdbviewer.model.HomeGridEntry
import ru.fbear.tmdbviewer.ui.profile.ProfileViewModel
import ru.fbear.tmdbviewer.ui.theme.TMDBviewerTheme

@Composable
fun Home(
    navController: NavController,
    viewModel: HomeViewModel,
    profileViewModel: ProfileViewModel
) {

    val popularMovies by viewModel.popularMovies.collectAsState(emptyList())
    val popularTVs by viewModel.popularTVs.collectAsState(emptyList())

    val isLogined by profileViewModel.isLogined.collectAsState()


    Home(
        popularMovies = popularMovies,
        popularTVs = popularTVs,
        isLogined = isLogined,
        isLiked = { id, type -> profileViewModel.isFavorite(id, type) },
        onLikeChange = { liked, id, type -> profileViewModel.markAsFavorite(liked, id, type) },
        onLoadMore = { type ->
            when (type) {
                Type.Movie -> viewModel.getPopularMovies()
                Type.TV -> viewModel.getPopularTVs()
            }
        },
        onItemClick = { type, item ->
            navController.navigate("detail/${type.string}/${item.id}")
        })
}


@Composable
private fun Home(
    popularMovies: List<HomeGridEntry>,
    popularTVs: List<HomeGridEntry>,
    isLogined: Boolean,
    isLiked: (Int, Type) -> Boolean,
    onLikeChange: suspend (Boolean, Int, Type) -> Unit,
    onLoadMore: (Type) -> Unit,
    onItemClick: (Type, HomeGridEntry) -> Unit
) {
    val tabs = listOf(
        TabItem.Movies,
        TabItem.TV
    )
    var selectedTab by remember { mutableStateOf<TabItem>(TabItem.Movies) }

    val scaffoldState = rememberScaffoldState()
    val context = LocalContext.current

    Scaffold(
        scaffoldState = scaffoldState,
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
                isLiked = isLiked,
                onLikedChange = { liked, id, type ->
                    if (isLogined) onLikeChange(liked, id, type)
                    else scaffoldState.snackbarHostState.showSnackbar(context.getString(R.string.need_login))
                },
                onLoadMore = onLoadMore,
                onItemClick = onItemClick
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
    name = "day theme",
    group = "themes",
    uiMode = Configuration.UI_MODE_NIGHT_NO
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
        Home(
            popularMovies = List(15) {
                object : HomeGridEntry {
                    override val id = it
                    override val posterPath: String? = null
                    override val name: String = "Movie $it"
                }
            },
            popularTVs = List(15) {
                object : HomeGridEntry {
                    override val id = it
                    override val posterPath: String? = null
                    override val name: String = "TV $it"
                }
            },
            isLogined = true,
            isLiked = { id, _ -> id % 2 == 0 },
            onLikeChange = { _, _, _ -> },
            onLoadMore = {},
            onItemClick = { _, _ -> }
        )
    }
}