package ru.fbear.tmdbviewer.ui.detail

import android.content.res.Configuration
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ru.fbear.tmdbviewer.R
import ru.fbear.tmdbviewer.model.Cast
import ru.fbear.tmdbviewer.ui.theme.TMDBviewerTheme

@Composable
fun DetailCastList(cast: List<Cast>) {
    Column(
        verticalArrangement = Arrangement.spacedBy(8.dp),
        modifier = Modifier.padding(8.dp)
    ) {
        Text(
            text = stringResource(R.string.cast),
            style = MaterialTheme.typography.h6
        )
        LazyRow {
            items(cast) {
                DetailCastListItem(
                    profilePath = it.profilePath,
                    name = it.name,
                    character = it.character
                )
            }
        }
    }
}

@Preview(
    name = "dark theme",
    group = "themes",
    uiMode = Configuration.UI_MODE_NIGHT_YES,
    heightDp = 356
)
@Preview(
    name = "ru lang",
    group = "language",
    locale = "RU",
    heightDp = 256
)
@Preview(
    name = "en lang",
    group = "language",
    locale = "EN",
    heightDp = 256
)
@Preview(
    name = "small font",
    group = "font scales",
    fontScale = 0.5f,
    heightDp = 256
)
@Preview(
    name = "large font",
    group = "font scales",
    fontScale = 1.5f,
    heightDp = 256
)
@Composable
fun DetailCastListPreview() {
    TMDBviewerTheme {
        DetailCastList(
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
            }

        )
    }
}