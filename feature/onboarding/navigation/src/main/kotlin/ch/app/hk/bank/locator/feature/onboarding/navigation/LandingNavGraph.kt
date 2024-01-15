package ch.app.hk.bank.locator.feature.onboarding.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import ch.app.hk.bank.locator.feature.onboarding.ui.LandingScreen

fun NavGraphBuilder.landingNavGraph(
    navController: NavController,
) {
    navigation(
        startDestination = LandingDestination.route,
        route = LandingNavGraphDestination.navGraphId,
    ) {
        composable(route = LandingDestination.route) {
            LandingScreen(
                back = navController::popBackStack,
            )
        }
    }
}
