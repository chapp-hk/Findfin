package ch.app.hk.bank.locator.feature.home.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import ch.app.hk.bank.locator.feature.auth.navigation.AuthNavGraphDestination
import ch.app.hk.bank.locator.feature.home.ui.container.view.HomeBottomNavigationLayout

fun NavGraphBuilder.homeNavGraph(navController: NavController) {
    navigation<HomeNavGraphDestination>(
        startDestination = HomeDestination,
    ) {
        composable<HomeDestination> {
            HomeBottomNavigationLayout {
                navController.navigate(
                    route = AuthNavGraphDestination(shouldCheckIsInit = false),
                )
            }
        }
    }
}
