package ru.fbear.tmdbviewer.ui.profile

import android.content.res.Configuration.UI_MODE_NIGHT_NO
import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import ru.fbear.tmdbviewer.R
import ru.fbear.tmdbviewer.Type
import ru.fbear.tmdbviewer.model.FavoriteListEntry
import ru.fbear.tmdbviewer.model.account.AccountDetails
import ru.fbear.tmdbviewer.model.account.Avatar
import ru.fbear.tmdbviewer.model.account.Gravatar
import ru.fbear.tmdbviewer.model.account.TmdbAvatar
import ru.fbear.tmdbviewer.ui.theme.TMDBviewerTheme

@Composable
fun Profile(navController: NavController, viewModel: ProfileViewModel) {

    val accountDetails by viewModel.accountDetails.collectAsState(null)

    val isLogined by viewModel.isLogined.collectAsState()

    val favoriteMovies by viewModel.favoriteMovies.collectAsState(emptyList())
    val favoriteTVs by viewModel.favoriteTVs.collectAsState(emptyList())

    Surface(
        color = MaterialTheme.colors.background,
        contentColor = MaterialTheme.colors.onBackground,
        modifier = Modifier.fillMaxSize()
    ) {
        if (isLogined) {
            if (accountDetails == null)
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) { CircularProgressIndicator() }
            else Profile(
                favoriteMovies = favoriteMovies,
                favoriteTVs = favoriteTVs,
                accountDetails = accountDetails!!,
                onLogout = { viewModel.logout() },
                onLoadMore = { type ->
                    when (type) {
                        Type.Movie -> viewModel.getFavoriteMovies()
                        Type.TV -> viewModel.getFavoriteTVs()
                    }
                },
                onFavoriteClick = { id, type -> navController.navigate("detail/${type.string}/$id") }
            )
        } else {
            ProfileLogin(onLoginRequest = { login, password -> viewModel.login(login, password) })
        }
    }
}

@Composable
private fun Profile(
    favoriteMovies: List<FavoriteListEntry>,
    favoriteTVs: List<FavoriteListEntry>,
    onLoadMore: (Type) -> Unit,
    accountDetails: AccountDetails,
    onLogout: () -> Unit,
    onFavoriteClick: (Int, Type) -> Unit
) {
    Surface(
        color = MaterialTheme.colors.background,
        contentColor = MaterialTheme.colors.onBackground,
        modifier = Modifier.fillMaxSize()
    ) {
        Column(
            verticalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier.verticalScroll(rememberScrollState())
        ) {
            ProfileHeader(
                gravatarHash = accountDetails.avatar.gravatar?.hash,
                tmdbAvatarPath = accountDetails.avatar.tmdb?.avatarPath,
                id = accountDetails.id,
                language = accountDetails.iso6391,
                region = accountDetails.iso31661,
                name = accountDetails.name,
                username = accountDetails.username
            )
            Divider()
            ProfileFavorite(
                favoriteMovies = favoriteMovies,
                favoriteTVs = favoriteTVs,
                onLoadMore = onLoadMore,
                onClick = onFavoriteClick
            )
            Button(
                onClick = onLogout,
                modifier = Modifier.align(Alignment.CenterHorizontally)
            ) {
                Text(text = stringResource(R.string.logout))
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
fun ProfilePreview() {
    TMDBviewerTheme {
        Profile(
            favoriteMovies = List(15) {
                object : FavoriteListEntry {
                    override val id = it
                    override val posterPath: String? = null
                    override val name = "Movie $it"
                }
            },
            favoriteTVs = List(15) {
                object : FavoriteListEntry {
                    override val id = it
                    override val posterPath: String? = null
                    override val name = "TV $it"
                }
            },
            accountDetails = AccountDetails(
                avatar = Avatar(Gravatar("205e460b479e2e5b48aec07710c08d50"), TmdbAvatar(null)),
                id = 1000000,
                includeAdult = false,
                iso6391 = "ru",
                iso31661 = "RU",
                name = "Steve",
                username = "SteveForever1990"
            ),
            onLoadMore = {},
            onLogout = {},
            onFavoriteClick = { _, _ -> }
        )
    }
}