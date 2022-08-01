package ru.fbear.tmdbviewer.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import ru.fbear.tmdbviewer.ui.theme.TMDBviewerTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TMDBviewerTheme {
                TMDBApp()
            }
        }
    }
}