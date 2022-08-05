package ru.fbear.tmdbviewer.ui.home

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Image
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import com.skydoves.landscapist.glide.GlideImage
import kotlinx.coroutines.launch
import ru.fbear.tmdbviewer.R
import ru.fbear.tmdbviewer.ui.theme.TMDBviewerTheme

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun HomeGridItem(
    posterPath: String?,
    title: String,
    isLiked: () -> Boolean,
    onClick: () -> Unit,
    onLikeChanged: suspend (Boolean) -> Unit
) {
    var liked by remember { mutableStateOf(isLiked()) }

    val coroutineScope = rememberCoroutineScope()

    Card(
        onClick = onClick,
        modifier = Modifier.aspectRatio(2 / 3.7F)
    ) {
        Box {
            IconButton(
                onClick = {
                    coroutineScope.launch {
                        try {
                            onLikeChanged(!liked)
                            liked = isLiked()
                        } catch (e: Exception) {
                            e.printStackTrace()
                        }
                    }
                },
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .zIndex(1F)
            ) {
                Icon(
                    imageVector = if (liked) Icons.Filled.Favorite else Icons.Filled.FavoriteBorder,
                    contentDescription = null
                )
            }
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier.padding(8.dp)
            ) {
                GlideImage(
                    imageModel = "https://image.tmdb.org/t/p/w185$posterPath".takeIf { posterPath != null },
                    contentScale = ContentScale.FillWidth,
                    loading = {
                        Box(
                            modifier = Modifier.fillMaxSize(),
                            contentAlignment = Alignment.Center
                        ) {
                            CircularProgressIndicator()
                        }
                    },
                    failure = {
                        Box(
                            modifier = Modifier.fillMaxSize(),
                            contentAlignment = Alignment.Center
                        ) {
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
        HomeGridItem(
            posterPath = "/pIkRyD18kl4FhoCNQuWxWu5cBLM.jpg",
            title = "Film name",
            isLiked = { return@HomeGridItem true },
            onClick = {},
            onLikeChanged = {}
        )
    }
}