package ru.fbear.tmdbviewer.ui

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import ru.fbear.tmdbviewer.ui.detail.Detail
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
        composable(
            route = "detail/{type}/{id}",
            arguments = listOf(
                navArgument("type") { type = NavType.StringType },
                navArgument("id") { type = NavType.IntType }
            )
        ) { backStackEntry ->
            val type = backStackEntry.arguments?.getString("type")
            val id = backStackEntry.arguments?.getInt("id")

            if (type == null || id == null)
                throw IllegalArgumentException("One of the arguments is null")

            Detail(type, id)
        }
        composable(route = Screen.Home.route) { Home(navController) }
        composable(route = Screen.Search.route) { Search() }
        composable(route = Screen.Profile.route) { Profile() }
    }
}