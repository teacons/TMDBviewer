package ru.fbear.tmdbviewer.ui.detail

import android.content.res.Configuration
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Image
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.skydoves.landscapist.glide.GlideImage
import ru.fbear.tmdbviewer.R
import ru.fbear.tmdbviewer.ui.theme.TMDBviewerTheme

@Composable
fun DetailCastListItem(profilePath: String?, name: String, character: String) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(8.dp),
        modifier = Modifier
            .padding(8.dp)
            .aspectRatio(2 / 3.5F)

    ) {
        GlideImage(
            imageModel = "https://image.tmdb.org/t/p/w185$profilePath",
            contentScale = ContentScale.FillWidth,
            loading = {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator()
                }
            },
            failure = {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Icon(
                        Icons.Filled.Image,
                        contentDescription = null,
                        modifier = Modifier.size(128.dp)
                    )
                }
            },
            previewPlaceholder = R.drawable.profile,
            modifier = Modifier.aspectRatio(2 / 3F)
        )
        Text(
            text = name,
            style = MaterialTheme.typography.subtitle1,
            textAlign = TextAlign.Center,
            overflow = TextOverflow.Ellipsis,
        )
        CompositionLocalProvider(LocalContentAlpha provides ContentAlpha.medium) {
            Text(
                text = character,
                style = MaterialTheme.typography.subtitle2,
                textAlign = TextAlign.Center,
                overflow = TextOverflow.Ellipsis,
            )
        }
    }
}

@Preview(
    name = "dark theme",
    group = "themes",
    uiMode = Configuration.UI_MODE_NIGHT_YES,
    widthDp = 256
)
@Preview(
    name = "ru lang",
    group = "language",
    locale = "RU",
    widthDp = 256
)
@Preview(
    name = "en lang",
    group = "language",
    locale = "EN",
    widthDp = 256
)
@Preview(
    name = "small font",
    group = "font scales",
    fontScale = 0.5f,
    widthDp = 256
)
@Preview(
    name = "large font",
    group = "font scales",
    fontScale = 1.5f,
    widthDp = 256
)
@Composable
fun DetailCastListItemPreview() {
    TMDBviewerTheme {
        DetailCastListItem(
            profilePath = "/jpurJ9jAcLCYjgHHfYF32m3zJYm.jpg\"",
            name = "Chris Hemsworth",
            character = "Thor Odinson"
        )
    }
}