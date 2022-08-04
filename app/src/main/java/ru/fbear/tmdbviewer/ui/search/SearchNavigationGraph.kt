package ru.fbear.tmdbviewer.ui.search

import androidx.navigation.*
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import ru.fbear.tmdbviewer.Type
import ru.fbear.tmdbviewer.ui.BottomNavScreen
import ru.fbear.tmdbviewer.ui.ScreenWithArgs


fun NavGraphBuilder.searchGraph(navController: NavController, viewModel: SearchViewModel) {
    navigation(startDestination = BottomNavScreen.Search.route, route = "search_graph") {

        composable(route = BottomNavScreen.Search.route) { Search(navController, viewModel) }

        composable(
            route = SearchScreen.SearchMore.route,
            arguments = SearchScreen.SearchMore.args
        ) { backStackEntry ->
            val typeString = backStackEntry.arguments?.getString("type")
                ?: throw IllegalArgumentException("type is null")

            val type: Type = Type.values().find { it.string == typeString }
                ?: throw IllegalArgumentException("Unknown type")

            SearchMore(navController = navController, type = type, searchViewModel = viewModel)
        }
    }
}

sealed class SearchScreen(override val route: String, override val args: List<NamedNavArgument>) :
    ScreenWithArgs {

    object SearchMore : SearchScreen(
        "search/more/{type}",
        listOf(navArgument("type") { type = NavType.StringType })
    )
}