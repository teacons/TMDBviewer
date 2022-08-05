package ru.fbear.tmdbviewer.ui.home

import androidx.compose.runtime.Composable
import ru.fbear.tmdbviewer.Type
import ru.fbear.tmdbviewer.model.HomeGridEntry

@Composable
fun HomeContent(
    selectedTab: TabItem,
    popularMovies: List<HomeGridEntry>,
    popularTVs: List<HomeGridEntry>,
    isLiked: (Int, Type) -> Boolean,
    onLikedChange: suspend (Boolean, Int, Type) -> Unit,
    onLoadMore: (Type) -> Unit,
    onItemClick: (Type, HomeGridEntry) -> Unit
) {
    when (selectedTab) {
        is TabItem.Movies -> HomeContentGrid(
            listItems = popularMovies,
            isLiked = { isLiked(it, Type.Movie) },
            onLikedChange = { liked, id ->
                onLikedChange(liked, id, Type.Movie)
            },
            onLoadMore = { onLoadMore(Type.Movie) },
            onItemClick = { item -> onItemClick(Type.Movie, item) }
        )
        is TabItem.TV -> HomeContentGrid(
            listItems = popularTVs,
            isLiked = { isLiked(it, Type.TV) },
            onLikedChange = { liked, id ->
                onLikedChange(liked, id, Type.TV)
            },
            onLoadMore = { onLoadMore(Type.TV) },
            onItemClick = { item -> onItemClick(Type.TV, item) }
        )
    }
}