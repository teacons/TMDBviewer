package ru.fbear.tmdbviewer.ui.search

import android.content.res.Configuration.UI_MODE_NIGHT_NO
import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.material.LocalContentColor
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedButton
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ru.fbear.tmdbviewer.R
import ru.fbear.tmdbviewer.model.SearchListEntry
import ru.fbear.tmdbviewer.ui.theme.TMDBviewerTheme

@Composable
fun SearchSubList(
    title: String,
    items: List<SearchListEntry>,
    totalResults: Int,
    isLiked: (Int) -> Boolean,
    onLikedChange: suspend (Boolean, Int) -> Unit,
    onShowAllClick: () -> Unit,
    onItemClick: (Int) -> Unit
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Text(
            text = title,
            style = MaterialTheme.typography.h5
        )
        items.subList(0, items.size.takeIf { 10 > it } ?: 10).forEach { item ->
            SearchListItem(
                posterPath = item.posterPath,
                title = item.name,
                voteAverage = item.voteAverage,
                isLiked = { isLiked(item.id) },
                onClick = { onItemClick(item.id) },
                onLikedChanged = { onLikedChange(it, item.id) }
            )
        }
        OutlinedButton(onClick = onShowAllClick) {
            Text(text = "${stringResource(R.string.showall)} ($totalResults)")
        }
    }
}


@Preview(
    name = "dark theme",
    group = "themes",
    uiMode = UI_MODE_NIGHT_YES,
)
@Preview(
    name = "day theme",
    group = "themes",
    uiMode = UI_MODE_NIGHT_NO,
)
@Preview(
    name = "ru lang",
    group = "language",
    locale = "RU",
)
@Preview(
    name = "en lang",
    group = "language",
    locale = "EN",
)
@Preview(
    name = "small font",
    group = "font scales",
    fontScale = 0.5f,
)
@Preview(
    name = "large font",
    group = "font scales",
    fontScale = 1.5f,
)
@Composable
fun SearchSubListPreview() {
    val items = List(15) {
        object : SearchListEntry {
            override val id = it
            override val posterPath: String? = null
            override val name = "Movie $it"
            override val voteAverage = it * 2 / 3F
        }
    }
    TMDBviewerTheme {
        CompositionLocalProvider(LocalContentColor provides MaterialTheme.colors.onBackground) {
            Box(modifier = Modifier.background(MaterialTheme.colors.background)) {
                SearchSubList(
                    "Films",
                    items,
                    15,
                    onShowAllClick = {},
                    onItemClick = {},
                    isLiked = { it % 2 == 0 },
                    onLikedChange = { _, _ -> }
                )
            }
        }
    }
}