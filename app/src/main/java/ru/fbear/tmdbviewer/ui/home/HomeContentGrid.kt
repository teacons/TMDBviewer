package ru.fbear.tmdbviewer.ui.home

import android.content.res.Configuration.UI_MODE_NIGHT_NO
import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.material.LocalContentColor
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ru.fbear.tmdbviewer.model.HomeGridEntry
import ru.fbear.tmdbviewer.ui.theme.TMDBviewerTheme
import ru.fbear.tmdbviewer.ui.utils.OnBottomReached

@Composable
fun HomeContentGrid(
    listItems: List<HomeGridEntry>,
    isLiked: (Int) -> Boolean,
    onLikedChange: suspend (Boolean, Int) -> Unit,
    onLoadMore: () -> Unit,
    onItemClick: (HomeGridEntry) -> Unit
) {
    val gridState = rememberLazyGridState()
    LazyVerticalGrid(
        columns = GridCells.Fixed(3),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp),
        state = gridState
    ) {
        items(listItems) { item ->
            HomeGridItem(
                posterPath = item.posterPath,
                title = item.name,
                isLiked = { isLiked(item.id) },
                onClick = { onItemClick(item) },
                onLikeChanged = { onLikedChange(it, item.id) }
            )
        }
    }

    gridState.OnBottomReached(onLoadMore)
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
fun HomeContentGridPreview() {
    val items = List(15) {
        object : HomeGridEntry {
            override val id = it
            override val posterPath = "/pIkRyD18kl4FhoCNQuWxWu5cBLM.jpg"
            override val name = "Thor: Love and Thunder"
        }
    }
    TMDBviewerTheme {
        CompositionLocalProvider(LocalContentColor provides MaterialTheme.colors.onBackground) {
            Box(modifier = Modifier.background(MaterialTheme.colors.background)) {
                HomeContentGrid(
                    listItems = items,
                    onLoadMore = {},
                    onItemClick = {},
                    isLiked = { return@HomeContentGrid it % 2 == 0 },
                    onLikedChange = { _, _ -> }
                )
            }
        }
    }
}