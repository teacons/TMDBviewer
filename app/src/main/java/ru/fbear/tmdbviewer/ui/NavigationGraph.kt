package ru.fbear.tmdbviewer.ui

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import ru.fbear.tmdbviewer.TMDBViewModel
import ru.fbear.tmdbviewer.Type
import ru.fbear.tmdbviewer.ui.detail.Detail
import ru.fbear.tmdbviewer.ui.home.Home
import ru.fbear.tmdbviewer.ui.profile.Profile
import ru.fbear.tmdbviewer.ui.search.Search
import ru.fbear.tmdbviewer.ui.search.SearchMore

@Composable
fun AppNavigationGraph(
    navController: NavHostController,
    modifier: Modifier
) {
    val viewModel: TMDBViewModel = viewModel()
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
            val typeString = backStackEntry.arguments?.getString("type")
                ?: throw IllegalArgumentException("type is null")

            val type: Type = Type.values().find { it.string == typeString }
                ?: throw IllegalArgumentException("Unknown type")

            val id = backStackEntry.arguments?.getInt("id")
                ?: throw IllegalArgumentException("id is null")

            Detail(type, id, viewModel)
        }
        composable(
            route = "search/more/{type}",
            arguments = listOf(
                navArgument("type") { type = NavType.StringType },
            )
        ) { backStackEntry ->
            val typeString = backStackEntry.arguments?.getString("type")
                ?: throw IllegalArgumentException("type is null")

            val type: Type = Type.values().find { it.string == typeString }
                ?: throw IllegalArgumentException("Unknown type")
            println("TEST Start SearchMore")
            SearchMore(navController = navController, type = type, viewModel = viewModel)
        }
        composable(route = Screen.Home.route) { Home(navController, viewModel) }
        composable(route = Screen.Search.route) { Search(navController, viewModel) }
        composable(route = Screen.Profile.route) { Profile() }
    }
}