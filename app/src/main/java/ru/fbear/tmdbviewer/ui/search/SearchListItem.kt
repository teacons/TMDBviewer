package ru.fbear.tmdbviewer.ui.search

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
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.em
import com.skydoves.landscapist.glide.GlideImage
import ru.fbear.tmdbviewer.R
import ru.fbear.tmdbviewer.ui.theme.TMDBviewerTheme
import kotlin.math.roundToInt

@Composable
fun SearchListItem(posterPath: String?, title: String, voteAverage: Float) {
    Card(
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
        ) {
            GlideImage(
                imageModel = "https://image.tmdb.org/t/p/w154$posterPath",
                contentScale = ContentScale.FillWidth,
                loading = {
                    Box(modifier = Modifier.fillMaxSize()) {
                        CircularProgressIndicator(
                            modifier = Modifier.align(Alignment.Center)
                        )
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
                previewPlaceholder = R.drawable.poster,
                modifier = Modifier
                    .aspectRatio(2 / 3F)
                    .weight(0.3F)
            )
            Text(
                text = title,
                style = MaterialTheme.typography.h5,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier
                    .weight(0.5F)
                    .padding(top = 8.dp)
            )
            CompositionLocalProvider(LocalContentAlpha provides ContentAlpha.medium) {
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier
                        .weight(0.2F)
                        .align(Alignment.Bottom)
                        .padding(bottom = 8.dp, end = 8.dp)
                ) {
                    CircularProgressIndicator(
                        progress = voteAverage / 10,
                        modifier = Modifier.size(50.dp)
                    )
                    Text(
                        text = "${(voteAverage * 10).roundToInt()}%",
                        style = MaterialTheme.typography.h6.copy(fontSize = 4.em),
                    )
                }
            }
        }
    }
}

@Preview(
    name = "dark theme",
    group = "themes",
    uiMode = Configuration.UI_MODE_NIGHT_YES
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
fun SearchListItemPreview() {
    TMDBviewerTheme {
        SearchListItem(
            posterPath = "/pIkRyD18kl4FhoCNQuWxWu5cBLM.jpg",
            title = "Thor: Love and Thunder",
            voteAverage = 6.7F
        )
    }
}