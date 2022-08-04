package ru.fbear.tmdbviewer.ui

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NamedNavArgument
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import ru.fbear.tmdbviewer.R
import ru.fbear.tmdbviewer.ui.detail.DetailViewModel
import ru.fbear.tmdbviewer.ui.detail.detailGraph
import ru.fbear.tmdbviewer.ui.home.Home
import ru.fbear.tmdbviewer.ui.home.HomeViewModel
import ru.fbear.tmdbviewer.ui.profile.Profile
import ru.fbear.tmdbviewer.ui.profile.ProfileViewModel
import ru.fbear.tmdbviewer.ui.search.SearchViewModel
import ru.fbear.tmdbviewer.ui.search.searchGraph

@Composable
fun AppNavigationGraph(
    navController: NavHostController,
    modifier: Modifier
) {
    val homeViewModel: HomeViewModel = viewModel()
    val searchViewModel: SearchViewModel = viewModel()
    val profileViewModel: ProfileViewModel = viewModel()
    val detailViewModel: DetailViewModel = viewModel()

    NavHost(
        navController = navController,
        startDestination = BottomNavScreen.Home.route,
//        startDestination = "home_graph",
        modifier = modifier
    ) {
        detailGraph(detailViewModel)
        composable(route = BottomNavScreen.Home.route) { Home(navController, homeViewModel) }
//        homeGraph(navController, homeViewModel)
        searchGraph(navController, searchViewModel)
        composable(route = BottomNavScreen.Profile.route) { Profile(profileViewModel) }
    }
}

sealed class BottomNavScreen(val title: Int, val icon: ImageVector, override val route: String) :
    Screen {
    object Home : BottomNavScreen(R.string.home, Icons.Filled.Home, "home")
    object Search : BottomNavScreen(R.string.search, Icons.Filled.Search, "search")
    object Profile : BottomNavScreen(R.string.profile, Icons.Filled.Person, "profile")
}

interface Screen {
    val route: String
}

interface ScreenWithArgs : Screen {
    val args: List<NamedNavArgument>
}