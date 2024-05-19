package ch.app.hk.bank.locator.feature.onboarding.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import ch.app.hk.bank.locator.feature.onboarding.ui.onboard.view.OnboardScreen
import ch.app.hk.bank.locator.feature.onboarding.ui.permission.view.RequestLocationPermissionScreen

fun NavGraphBuilder.onboardingNavGraph(
    navController: NavController,
    finishOnboarding: () -> Unit,
) {
    navigation<OnboardingNavGraphDestination>(
        startDestination = OnboardingDestination,
    ) {
        composable<OnboardingDestination> {
            OnboardScreen(
                goToRequestPermission = {
                    navController.navigate(OnboardingRequestPermissionDestination) {
                        popUpTo<OnboardingNavGraphDestination> {
                            inclusive = true
                        }
                    }
                },
                finishOnboarding = finishOnboarding,
            )
        }

        composable<OnboardingRequestPermissionDestination> {
            RequestLocationPermissionScreen(finishOnboarding = finishOnboarding)
        }
    }
}
