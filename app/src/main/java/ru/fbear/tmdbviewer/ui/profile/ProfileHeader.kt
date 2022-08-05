package ru.fbear.tmdbviewer.ui.profile

import android.content.res.Configuration
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Face
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
fun ProfileHeader(
    gravatarHash: String?,
    tmdbAvatarPath: String?,
    id: Int,
    language: String,
    region: String,
    name: String,
    username: String
) {
    val gravatarLink =
        "https://www.gravatar.com/avatar/$gravatarHash?s=200".takeIf { gravatarHash != null }
    val tmdbAvatarLink =
        "https://image.tmdb.org/t/p/w185$tmdbAvatarPath".takeIf { tmdbAvatarPath != null }

    Row(
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
    ) {
        GlideImage(
            imageModel = tmdbAvatarLink ?: (gravatarLink ?: Icons.Filled.Face),
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
            previewPlaceholder = R.drawable.avatar,
            modifier = Modifier
                .aspectRatio(1F)
                .weight(0.3F)
        )
        Column(
            verticalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier
                .weight(0.7F)
                .padding(top = 8.dp, end = 8.dp)
        ) {
            Text(
                text = username,
                style = MaterialTheme.typography.h5,
                overflow = TextOverflow.Ellipsis
            )
            Column {
                CompositionLocalProvider(LocalContentAlpha provides ContentAlpha.medium) {
                    if (name.isNotEmpty()) {
                        Text(
                            text = "${stringResource(id = R.string.name)}: $name",
                            style = MaterialTheme.typography.subtitle1,
                            overflow = TextOverflow.Ellipsis,
                            maxLines = 1
                        )
                    }
                    Text(
                        text = "ID: $id",
                        style = MaterialTheme.typography.subtitle1,
                        overflow = TextOverflow.Ellipsis,
                        maxLines = 1
                    )
                    Text(
                        text = "${stringResource(R.string.language)}: $language-$region",
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
fun ProfileHeaderPreview() {
    TMDBviewerTheme {
        ProfileHeader(
            gravatarHash = "205e460b479e2e5b48aec07710c08d50",
            tmdbAvatarPath = null,
            id = 1000000,
            language = "ru",
            region = "RU",
            name = "Steve",
            username = "SteveForever1990"
        )
    }
}