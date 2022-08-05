package ru.fbear.tmdbviewer.ui.profile

import android.content.res.Configuration
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
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
fun ProfileFavoriteItem(posterPath: String?, name: String, onClick: () -> Unit) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(8.dp),
        modifier = Modifier
            .clickable { onClick() }
            .padding(8.dp)
            .width(128.dp)
            .aspectRatio(2 / 4.5F)
    ) {
        GlideImage(
            imageModel = "https://image.tmdb.org/t/p/w185$posterPath".takeIf { posterPath != null },
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
            previewPlaceholder = R.drawable.poster,
            modifier = Modifier
                .aspectRatio(2 / 3F)
                .weight(0.7F)
        )
        Text(
            text = name,
            style = MaterialTheme.typography.subtitle1,
            textAlign = TextAlign.Center,
            overflow = TextOverflow.Ellipsis,
            modifier = Modifier.weight(0.3F)
        )

    }
}

@Preview(
    name = "dark theme",
    group = "themes",
    uiMode = Configuration.UI_MODE_NIGHT_YES,
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
fun ProfileFavoriteItemPreview() {
    TMDBviewerTheme {
        ProfileFavoriteItem(
            posterPath = "/pIkRyD18kl4FhoCNQuWxWu5cBLM.jpg",
            name = "Thor: Love and Thunder",
            onClick = {}
        )
    }
}