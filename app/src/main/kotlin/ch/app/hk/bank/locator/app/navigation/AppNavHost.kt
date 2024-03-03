package ch.app.hk.bank.locator.app.navigation

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import ch.app.hk.bank.locator.feature.auth.navigation.AuthNavGraphDestination
import ch.app.hk.bank.locator.feature.auth.navigation.authNavGraph
import ch.app.hk.bank.locator.feature.home.navigation.HomeNavGraphDestination
import ch.app.hk.bank.locator.feature.home.navigation.homeNavGraph
import ch.app.hk.bank.locator.feature.onboarding.navigation.OnboardingNavGraphDestination
import ch.app.hk.bank.locator.feature.onboarding.navigation.onboardingNavGraph

@Composable
fun AppNavHost(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
) {
    NavHost(
        modifier = modifier.fillMaxSize(),
        navController = navController,
        startDestination = OnboardingNavGraphDestination.navGraphId,
    ) {
        onboardingNavGraph(
            navController = navController,
            finishOnboarding = {
                navController.navigate(AuthNavGraphDestination.navGraphId) {
                    launchSingleTop = true
                    popUpTo(OnboardingNavGraphDestination.navGraphId) {
                        inclusive = true
                    }
                }
            },
        )

        authNavGraph(
            navController = navController,
            finishAuth = {
                navController.navigate(HomeNavGraphDestination.navGraphId) {
                    launchSingleTop = true
                    popUpTo(AuthNavGraphDestination.navGraphId) {
                        inclusive = true
                    }
                }
            },
        )

        homeNavGraph(navController)
    }
}
