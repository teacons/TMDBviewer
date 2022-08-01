package ru.fbear.tmdbviewer.ui.home

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Image
import androidx.compose.runtime.Composable
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
fun HomeGridItem(posterPath: String?, title: String) {
    Card(
        modifier = Modifier.aspectRatio(2 / 3.7F)
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier.padding(8.dp)
        ) {
            GlideImage(
                imageModel = "https://image.tmdb.org/t/p/w185$posterPath",
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
                modifier = Modifier.aspectRatio(2 / 3F)
            )
            Text(
                text = title,
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
    uiMode = UI_MODE_NIGHT_YES,
    widthDp = 128
)
@Preview(
    name = "ru lang",
    group = "language",
    locale = "RU",
    widthDp = 128
)
@Preview(
    name = "en lang",
    group = "language",
    locale = "EN",
    widthDp = 128
)
@Preview(
    name = "small font",
    group = "font scales",
    fontScale = 0.5f,
    widthDp = 128
)
@Preview(
    name = "large font",
    group = "font scales",
    fontScale = 1.5f,
    widthDp = 128
)
@Composable
fun HomeGridItemPreview() {
    TMDBviewerTheme {
        HomeGridItem(posterPath = null, title = "Test")
    }
}