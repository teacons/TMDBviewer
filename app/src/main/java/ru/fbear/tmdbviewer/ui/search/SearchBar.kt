package ru.fbear.tmdbviewer.ui.search

import android.content.res.Configuration.UI_MODE_NIGHT_NO
import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ru.fbear.tmdbviewer.ui.theme.TMDBviewerTheme

@Composable
fun SearchBar(value: String, onValueChange: (String) -> Unit) {
    TextField(
        value = value,
        onValueChange = onValueChange,
        leadingIcon = { Icon(Icons.Filled.Search, contentDescription = "") },
        trailingIcon = {
            if (value != "") {
                IconButton(onClick = { onValueChange("") }) {
                    Icon(Icons.Filled.Close, contentDescription = "")
                }
            }
        },
        singleLine = true,
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
    )
}


@Preview(
    name = "dark theme",
    group = "themes",
    uiMode = UI_MODE_NIGHT_YES,
)
@Preview(
    name = "day theme",
    group = "themes",
    uiMode = UI_MODE_NIGHT_NO,
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
fun SearchBarPreview() {
    TMDBviewerTheme {
        CompositionLocalProvider(LocalContentColor provides MaterialTheme.colors.onBackground) {
            Box(modifier = Modifier.background(MaterialTheme.colors.background)) {
                SearchBar("Thor: Love and Thunder") {}
            }
        }
    }
}