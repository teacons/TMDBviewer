package ru.fbear.tmdbviewer.ui.home

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.material.Icon
import androidx.compose.material.LeadingIconTab
import androidx.compose.material.TabRow
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LiveTv
import androidx.compose.material.icons.filled.Movie
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import ru.fbear.tmdbviewer.R
import ru.fbear.tmdbviewer.ui.theme.TMDBviewerTheme

@Composable
fun HomeGridTabs(
    tabs: List<TabItem>,
    selectedTab: TabItem,
    onSelectedTabChange: (TabItem) -> Unit
) {
    TabRow(selectedTabIndex = tabs.indexOf(selectedTab)) {
        tabs.forEach { tab ->
            LeadingIconTab(
                icon = { Icon(imageVector = tab.icon, contentDescription = null) },
                selected = selectedTab == tab,
                onClick = { onSelectedTabChange(tab) },
                text = { Text(stringResource(id = tab.title)) })
        }
    }
}

sealed class TabItem(
    var icon: ImageVector,
    var title: Int
) {
    object Movies : TabItem(Icons.Filled.Movie, R.string.movies)
    object TV : TabItem(Icons.Filled.LiveTv, R.string.tv)
}

@Preview(
    name = "dark theme",
    group = "themes",
    uiMode = UI_MODE_NIGHT_YES,
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
fun HomeGridTabsPreview() {
    TMDBviewerTheme {
        HomeGridTabs(
            tabs = listOf(TabItem.Movies, TabItem.TV),
            selectedTab = TabItem.Movies,
            onSelectedTabChange = {})
    }
}