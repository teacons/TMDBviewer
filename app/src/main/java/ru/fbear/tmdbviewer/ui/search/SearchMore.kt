package ru.fbear.tmdbviewer.ui.search

import android.content.res.Configuration
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import ru.fbear.tmdbviewer.Type
import ru.fbear.tmdbviewer.model.SearchListEntry
import ru.fbear.tmdbviewer.ui.theme.TMDBviewerTheme
import ru.fbear.tmdbviewer.ui.utils.OnBottomReached

@Composable
fun SearchMore(navController: NavController, type: Type, searchViewModel: SearchViewModel) {

    val searched: List<SearchListEntry> by when (type) {
        Type.Movie -> searchViewModel.searchedMovies.collectAsState()
        Type.TV -> searchViewModel.searchedTVs.collectAsState()
    }

    val columnState = rememberLazyListState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = stringResource(id = type.title)) },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(imageVector = Icons.Filled.ArrowBack, contentDescription = null)
                    }
                }
            )
        }
    ) { innerPadding ->
        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(8.dp),
            state = columnState,
            modifier = Modifier.padding(innerPadding)
        ) {
            items(searched) { item ->
                SearchListItem(
                    posterPath = item.posterPath,
                    title = item.name,
                    voteAverage = item.voteAverage,
                    onClick = { navController.navigate("detail/${type.string}/${item.id}") }
                )
            }
        }

        columnState.OnBottomReached {
            when (type) {
                Type.Movie -> searchViewModel.searchMovies()
                Type.TV -> searchViewModel.searchTV()
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
fun SearchMorePreview() {
    TMDBviewerTheme {
        SearchMore(rememberNavController(), Type.Movie, viewModel())
    }
}
