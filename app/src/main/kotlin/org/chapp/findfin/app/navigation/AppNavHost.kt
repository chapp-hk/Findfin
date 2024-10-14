package org.chapp.findfin.app.navigation

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import org.chapp.findfin.feature.auth.navigation.graph.AuthNavGraphDestination
import org.chapp.findfin.feature.auth.navigation.graph.AuthOnboardingNavGraphDestination
import org.chapp.findfin.feature.auth.navigation.graph.authNavGraph
import org.chapp.findfin.feature.auth.navigation.graph.authOnboardingNavGraph
import org.chapp.findfin.feature.home.navigation.HomeNavGraphDestination
import org.chapp.findfin.feature.home.navigation.homeNavGraph
import org.chapp.findfin.feature.onboarding.presentation.navigation.graph.OnboardingNavGraphDestination
import org.chapp.findfin.feature.onboarding.presentation.navigation.graph.onboardingNavGraph

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
