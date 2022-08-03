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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.skydoves.landscapist.glide.GlideImage
import ru.fbear.tmdbviewer.R
import ru.fbear.tmdbviewer.ui.theme.TMDBviewerTheme

@Composable
fun DetailHeader(title: String, year: Int, runtime: Int, posterPath: String?) {
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
        Column(
            verticalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier
                .weight(0.7F)
                .padding(top = 8.dp, end = 8.dp)
        ) {
            Text(
                text = title,
                style = MaterialTheme.typography.h5,
                overflow = TextOverflow.Ellipsis
            )
            Column {
                CompositionLocalProvider(LocalContentAlpha provides ContentAlpha.medium) {
                    Text(
                        text = "${stringResource(R.string.year)}: $year",
                        style = MaterialTheme.typography.subtitle1,
                        overflow = TextOverflow.Ellipsis,
                        maxLines = 1
                    )
                    Text(
                        text = buildString {
                            append(stringResource(R.string.runtime))
                            append(": ")
                            append(runtime / 60)
                            append(stringResource(R.string.hours))
                            append(" ")
                            append(runtime % 60)
                            append(stringResource(R.string.minutes))
                        },
                        style = MaterialTheme.typography.subtitle1,
                        overflow = TextOverflow.Ellipsis,
                        maxLines = 1
                    )
                }
            }
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
fun DetailHeaderPreview() {
    TMDBviewerTheme {
        DetailHeader(
            title = "Thor: Love and Thunder",
            year = 2022,
            runtime = 119,
            posterPath = "/pIkRyD18kl4FhoCNQuWxWu5cBLM.jpg"
        )
    }
}