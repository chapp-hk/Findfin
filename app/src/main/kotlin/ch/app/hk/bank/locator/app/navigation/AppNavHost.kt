package ch.app.hk.bank.locator.app.navigation

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import ch.app.hk.bank.locator.feature.auth.navigation.graph.AuthNavGraphDestination
import ch.app.hk.bank.locator.feature.auth.navigation.graph.AuthOnboardingNavGraphDestination
import ch.app.hk.bank.locator.feature.auth.navigation.graph.authNavGraph
import ch.app.hk.bank.locator.feature.auth.navigation.graph.authOnboardingNavGraph
import ch.app.hk.bank.locator.feature.home.navigation.HomeNavGraphDestination
import ch.app.hk.bank.locator.feature.home.navigation.homeNavGraph
import ch.app.hk.bank.locator.feature.onboarding.navigation.graph.OnboardingNavGraphDestination
import ch.app.hk.bank.locator.feature.onboarding.navigation.graph.onboardingNavGraph

@Composable
fun AppNavHost(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
) {
    NavHost(
        modifier = modifier.fillMaxSize(),
        navController = navController,
        startDestination = OnboardingNavGraphDestination,
    ) {
        onboardingNavGraph(
            navController = navController,
            finishOnboarding = {
                navController.navigate(AuthOnboardingNavGraphDestination) {
                    launchSingleTop = true
                    popUpTo<OnboardingNavGraphDestination> {
                        inclusive = true
                    }
                }
            },
        )

        authOnboardingNavGraph(
            navController = navController,
            onFinishAuthOnboarding = {
                navController.navigate(HomeNavGraphDestination) {
                    launchSingleTop = true
                    popUpTo<AuthOnboardingNavGraphDestination> {
                        inclusive = true
                    }
                }
            },
        )

        authNavGraph(
            navController = navController,
            finishAuth = {
                navController.navigate(HomeNavGraphDestination) {
                    launchSingleTop = true
                    popUpTo<AuthNavGraphDestination> {
                        inclusive = true
                    }
                }
            },
        )

        homeNavGraph(navController)
    }
}
