package ch.app.hk.bank.locator.feature.onboarding.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import ch.app.hk.bank.locator.feature.onboarding.ui.onboard.screen.OnboardScreen
import ch.app.hk.bank.locator.feature.onboarding.ui.permission.screen.RequestLocationPermissionScreen

fun NavGraphBuilder.onboardingNavGraph(
    navController: NavController,
    goToHome: () -> Unit,
) {
    navigation(
        startDestination = OnboardingDestination.route,
        route = OnboardingNavGraphDestination.navGraphId,
    ) {
        composable(route = OnboardingDestination.route) {
            OnboardScreen(
                goToRequestPermission = {
                    navController.navigate(OnboardingRequestPermissionDestination.route) {
                        popUpTo(OnboardingNavGraphDestination.navGraphId) {
                            inclusive = true
                        }
                    }
                },
                goToHome = goToHome,
            )
        }

        composable(route = OnboardingRequestPermissionDestination.route) {
            RequestLocationPermissionScreen(goToHome = goToHome)
        }
    }
}
