package ch.app.hk.bank.locator.feature.home.navigation

import androidx.compose.material3.Text
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation

fun NavGraphBuilder.homeNavGraph(navController: NavController) {
    navigation(
        startDestination = HomeDestination.route,
        route = HomeNavGraphDestination.navGraphId,
    ) {
        // TODO - proper use of navController
        navController.toString()
        composable(route = HomeDestination.route) {
            Text(text = "home")
        }
    }
}
