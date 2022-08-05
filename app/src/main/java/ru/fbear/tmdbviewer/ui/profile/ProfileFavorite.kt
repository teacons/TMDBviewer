package ru.fbear.tmdbviewer.ui.profile

import androidx.compose.material.Divider
import androidx.compose.runtime.Composable
import ru.fbear.tmdbviewer.Type
import ru.fbear.tmdbviewer.model.FavoriteListEntry

@Composable
fun ProfileFavorite(
    favoriteMovies: List<FavoriteListEntry>,
    favoriteTVs: List<FavoriteListEntry>,
    onLoadMore: (Type) -> Unit,
    onClick: (Int, Type) -> Unit
) {
    ProfileFavoriteList(
        type = Type.Movie,
        favoriteList = favoriteMovies,
        onLoadMore = onLoadMore,
        onClick = { onClick(it, Type.Movie) })
    if (favoriteMovies.isNotEmpty()) Divider()
    ProfileFavoriteList(
        type = Type.TV,
        favoriteList = favoriteTVs,
        onLoadMore = onLoadMore,
        onClick = { onClick(it, Type.TV) })
}