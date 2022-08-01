package ru.fbear.tmdbviewer.ui

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import ru.fbear.tmdbviewer.ui.home.Home
import ru.fbear.tmdbviewer.ui.profile.Profile
import ru.fbear.tmdbviewer.ui.search.Search

@Composable
fun AppNavigationGraph(
    navController: NavHostController,
    modifier: Modifier
) {
    NavHost(
        navController = navController,
        startDestination = Screen.Home.route,
        modifier = modifier
    ) {
        composable(route = Screen.Home.route) { Home() }
        composable(route = Screen.Search.route) { Search() }
        composable(route = Screen.Profile.route) { Profile() }
    }
}