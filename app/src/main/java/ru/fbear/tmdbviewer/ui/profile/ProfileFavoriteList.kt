package ru.fbear.tmdbviewer.ui.profile

import android.content.res.Configuration.UI_MODE_NIGHT_NO
import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.LocalContentColor
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ru.fbear.tmdbviewer.Type
import ru.fbear.tmdbviewer.model.FavoriteListEntry
import ru.fbear.tmdbviewer.ui.theme.TMDBviewerTheme
import ru.fbear.tmdbviewer.ui.utils.OnBottomReached

@Composable
fun ProfileFavoriteList(
    type: Type,
    favoriteList: List<FavoriteListEntry>,
    onLoadMore: (Type) -> Unit,
    onClick: (Int) -> Unit
) {

    val listState = rememberLazyListState()

    Column(
        verticalArrangement = Arrangement.spacedBy(8.dp),
        modifier = Modifier.padding(8.dp)
    ) {
        if (favoriteList.isNotEmpty()) {
            Text(
                text = stringResource(type.favoriteTitle),
                style = MaterialTheme.typography.h6
            )
        }
        LazyRow(
            state = listState,
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(favoriteList) { item ->
                ProfileFavoriteItem(
                    posterPath = item.posterPath,
                    name = item.name,
                    onClick = { onClick(item.id) }
                )
            }
        }

        listState.OnBottomReached { onLoadMore(type) }

    }
}


@Preview(
    name = "dark theme",
    group = "themes",
    uiMode = UI_MODE_NIGHT_YES,
    heightDp = 350
)
@Preview(
    name = "day theme",
    group = "themes",
    uiMode = UI_MODE_NIGHT_NO,
    heightDp = 350
)
@Preview(
    name = "ru lang",
    group = "language",
    locale = "RU",
    heightDp = 350
)
@Preview(
    name = "en lang",
    group = "language",
    locale = "EN",
    heightDp = 350
)
@Preview(
    name = "small font",
    group = "font scales",
    fontScale = 0.5f,
    heightDp = 350
)
@Preview(
    name = "large font",
    group = "font scales",
    fontScale = 1.5f,
    heightDp = 350
)
@Composable
fun ProfileFavoriteListPreview() {
    TMDBviewerTheme {
        CompositionLocalProvider(LocalContentColor provides MaterialTheme.colors.onBackground) {
            Box(modifier = Modifier.background(MaterialTheme.colors.background)) {
                ProfileFavoriteList(
                    type = Type.Movie,
                    favoriteList = List(15) {
                        object : FavoriteListEntry {
                            override val id = it
                            override val posterPath = "/pIkRyD18kl4FhoCNQuWxWu5cBLM.jpg"
                            override val name: String = "Thor: Love and Thunder"
                        }
                    },
                    onLoadMore = {},
                    onClick = {}
                )
            }
        }
    }
}