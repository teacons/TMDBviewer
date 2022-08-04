package ru.fbear.tmdbviewer.ui.detail

import androidx.navigation.NamedNavArgument
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.navArgument
import ru.fbear.tmdbviewer.Type
import ru.fbear.tmdbviewer.ui.ScreenWithArgs

fun NavGraphBuilder.detailGraph(viewModel: DetailViewModel) {
    navigation(startDestination = DetailScreen.Detail.route, route = "detail_graph") {
        composable(
            route = DetailScreen.Detail.route,
            arguments = DetailScreen.Detail.args
        ) { backStackEntry ->
            val typeString = backStackEntry.arguments?.getString("type")
                ?: throw IllegalArgumentException("type is null")

            val type: Type = Type.values().find { it.string == typeString }
                ?: throw IllegalArgumentException("Unknown type")

            val id = backStackEntry.arguments?.getInt("id")
                ?: throw IllegalArgumentException("id is null")

            Detail(type, id, viewModel)
        }
    }
}

sealed class DetailScreen(override val route: String, override val args: List<NamedNavArgument>) :
    ScreenWithArgs {
    object Detail : DetailScreen(
        "detail/{type}/{id}",
        listOf(
            navArgument("type") { type = NavType.StringType },
            navArgument("id") { type = NavType.IntType })
    )
}