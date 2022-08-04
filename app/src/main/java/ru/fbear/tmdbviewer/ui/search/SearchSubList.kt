package ru.fbear.tmdbviewer.ui.search

import android.content.res.Configuration
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedButton
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ru.fbear.tmdbviewer.R
import ru.fbear.tmdbviewer.SearchListEntry
import ru.fbear.tmdbviewer.ui.theme.TMDBviewerTheme

@Composable
fun SearchSubList(
    title: String,
    items: List<SearchListEntry>,
    totalResults: Int,
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
                onClick = { onItemClick(item.id) }
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
    uiMode = Configuration.UI_MODE_NIGHT_YES,
    showBackground = true
)
@Preview(
    name = "ru lang",
    group = "language",
    locale = "RU",
    showBackground = true
)
@Preview(
    name = "en lang",
    group = "language",
    locale = "EN",
    showBackground = true
)
@Preview(
    name = "small font",
    group = "font scales",
    fontScale = 0.5f,
    showBackground = true
)
@Preview(
    name = "large font",
    group = "font scales",
    fontScale = 1.5f,
    showBackground = true
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
        SearchSubList("Films", items, 15, onShowAllClick = {}, onItemClick = {})
    }
}