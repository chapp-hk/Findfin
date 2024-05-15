package ch.app.hk.bank.locator.feature.home.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import ch.app.hk.bank.locator.feature.home.ui.container.screen.HomeContainerScreen

fun NavGraphBuilder.homeNavGraph(navController: NavController) {
    navigation<HomeNavGraphDestination>(
        startDestination = HomeDestination,
    ) {
        // TODO - proper use of navController
        navController.toString()
        composable<HomeDestination> {
            HomeContainerScreen()
        }
    }
}
