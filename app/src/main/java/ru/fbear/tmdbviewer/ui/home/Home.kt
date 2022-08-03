package ru.fbear.tmdbviewer.ui.home

import android.content.res.Configuration
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import ru.fbear.tmdbviewer.ui.theme.TMDBviewerTheme

@Composable
fun Home() {
    val tabs = listOf(
        TabItem.Movies,
        TabItem.TV
    )
    var selectedTab by remember { mutableStateOf<TabItem>(TabItem.Movies) }

    Scaffold(
        topBar = {
            HomeGridTabs(
                tabs = tabs,
                selectedTab = selectedTab,
                onSelectedTabChange = { selectedTab = it })
        }
    ) { innerPadding ->
        Box(modifier = Modifier.padding(innerPadding)) {
            HomeContent(selectedTab = selectedTab)
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
fun HomePreview() {
    TMDBviewerTheme {
        Home()
    }
}