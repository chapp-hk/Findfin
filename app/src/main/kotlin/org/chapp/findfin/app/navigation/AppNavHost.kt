package org.chapp.findfin.app.navigation

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import org.chapp.findfin.feature.auth.presentation.navigation.graph.AuthNavGraphDestination
import org.chapp.findfin.feature.auth.presentation.navigation.graph.authNavGraph
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
            finishOnboarding = {
                navController.navigate(HomeNavGraphDestination) {
                    launchSingleTop = true
                    popUpTo<OnboardingNavGraphDestination> {
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
