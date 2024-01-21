package ch.app.hk.bank.locator.feature.onboarding.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import ch.app.hk.bank.locator.feature.onboarding.ui.onboard.screen.OnboardScreen

fun NavGraphBuilder.onboardingNavGraph(
    navController: NavController,
) {
    navigation(
        startDestination = OnboardingDestination.route,
        route = OnboardingNavGraphDestination.navGraphId,
    ) {
        composable(route = OnboardingDestination.route) {
            OnboardScreen(
            )
        }
    }
}
