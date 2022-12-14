package ru.fbear.tmdbviewer.ui.detail

import android.content.res.Configuration.UI_MODE_NIGHT_NO
import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import kotlinx.coroutines.launch
import ru.fbear.tmdbviewer.R
import ru.fbear.tmdbviewer.Type
import ru.fbear.tmdbviewer.model.detail.Cast
import ru.fbear.tmdbviewer.model.detail.Genre
import ru.fbear.tmdbviewer.ui.profile.ProfileViewModel
import ru.fbear.tmdbviewer.ui.theme.TMDBviewerTheme
import kotlin.math.roundToInt

@Composable
fun Detail(
    type: Type,
    id: Int,
    navController: NavController,
    viewModel: DetailViewModel,
    profileViewModel: ProfileViewModel
) {
    var contentIsReady by remember(id) { mutableStateOf(false) }

    var title by remember(id) { mutableStateOf<String?>(null) }
    var year by remember(id) { mutableStateOf<Int?>(0) }
    var runtime by remember(id) { mutableStateOf<Int?>(null) }
    var genres by remember(id) { mutableStateOf<List<Genre>?>(null) }
    var posterPath by remember(id) { mutableStateOf<String?>(null) }
    var overview by remember(id) { mutableStateOf<String?>(null) }
    var cast by remember(id) { mutableStateOf<List<Cast>?>(null) }

    val isLogined by profileViewModel.isLogined.collectAsState()

    LaunchedEffect(Unit) {
        when (type) {
            Type.TV -> {
                val tvDetail = try {
                    viewModel.getTVDetails(id)
                } catch (e: Exception) {
                    return@LaunchedEffect
                }
                with(tvDetail) {
                    title = name
                    year = firstAirDate.split('-').first().toInt()
                    runtime = episodeRunTime.average().takeIf { !it.isNaN() }?.roundToInt()
                    genres = this.genres
                    posterPath = this.posterPath
                    overview = this.overview
                    cast = credits.cast
                }
                contentIsReady = true
            }
            Type.Movie -> {
                val movieDetail = try {
                    viewModel.getMovieDetails(id)
                } catch (e: Exception) {
                    return@LaunchedEffect
                }

                with(movieDetail) {
                    title = this.title
                    year = releaseDate.split('-').first().toInt()
                    runtime = this.runtime
                    genres = this.genres
                    posterPath = this.posterPath
                    overview = this.overview
                    cast = credits.cast
                }
                contentIsReady = true
            }
        }
    }

    if (contentIsReady) {
        Detail(
            title = title!!,
            year = year!!,
            runtime = runtime!!,
            genres = genres!!,
            posterPath = posterPath,
            overview = overview!!,
            cast = cast!!,
            isLogined = isLogined,
            isLiked = { profileViewModel.isFavorite(id, type) },
            onLikeChanged = { profileViewModel.markAsFavorite(it, id, type) },
            onBackPressed = { navController.popBackStack() }
        )
    } else {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator()
        }
    }


}

@Composable
private fun Detail(
    title: String,
    year: Int,
    runtime: Int,
    genres: List<Genre>,
    posterPath: String?,
    overview: String,
    cast: List<Cast>,
    isLogined: Boolean,
    isLiked: () -> Boolean,
    onLikeChanged: suspend (Boolean) -> Unit,
    onBackPressed: () -> Unit
) {

    val coroutineScope = rememberCoroutineScope()

    var liked by remember { mutableStateOf(isLiked()) }

    val scaffoldState = rememberScaffoldState()

    val context = LocalContext.current

    Scaffold(
        scaffoldState = scaffoldState,
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = title,
                        overflow = TextOverflow.Ellipsis,
                        maxLines = 1
                    )
                },
                navigationIcon = {
                    IconButton(onClick = onBackPressed) {
                        Icon(imageVector = Icons.Filled.ArrowBack, contentDescription = null)
                    }
                },
                actions = {
                    IconButton(onClick = {
                        coroutineScope.launch {
                            if (isLogined) {
                                onLikeChanged(!liked)
                                liked = isLiked()
                            } else {
                                scaffoldState.snackbarHostState.showSnackbar(context.getString(R.string.need_login))
                            }
                        }
                    }) {
                        Icon(
                            imageVector = if (liked) Icons.Filled.Favorite else Icons.Filled.FavoriteBorder,
                            contentDescription = null
                        )
                    }
                }
            )
        }
    ) { innerPadding ->
        Column(
            verticalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier
                .verticalScroll(rememberScrollState())
                .padding(innerPadding)
        ) {
            DetailHeader(title = title, year = year, runtime = runtime, posterPath = posterPath)
            Divider()
            DetailGenres(genres = genres)
            Divider()
            DetailOverview(overview = overview)
            Divider()
            DetailCastList(cast = cast)
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
fun DetailPreview() {
    TMDBviewerTheme {
        Detail(
            title = "Thor: Love and Thunder",
            year = 2022,
            runtime = 119,
            genres = listOf(Genre(28, "Action"), Genre(12, "Adventure"), Genre(14, "Fantasy")),
            posterPath = "/pIkRyD18kl4FhoCNQuWxWu5cBLM.jpg",
            overview = "After his retirement is interrupted by Gorr the God Butcher, a galactic killer who seeks the extinction of the gods, Thor enlists the help of King Valkyrie, Korg, and ex-girlfriend Jane Foster, who now inexplicably wields Mjolnir as the Mighty Thor. Together they embark upon a harrowing cosmic adventure to uncover the mystery of the God Butcher???s vengeance and stop him before it???s too late.",
            cast = List(15) {
                Cast(
                    adult = false,
                    character = "Thor Odinson",
                    creditId = "62c8c25290b87e00f53973fb",
                    gender = 2,
                    id = 74568,
                    knownForDepartment = "Acting",
                    name = "Chris Hemsworth",
                    order = 0,
                    originalName = "Chris Hemsworth",
                    popularity = 151.528F,
                    profilePath = "/jpurJ9jAcLCYjgHHfYF32m3zJYm.jpg"
                )
            },
            isLiked = { true },
            onLikeChanged = {},
            onBackPressed = {},
            isLogined = true
        )
    }
}