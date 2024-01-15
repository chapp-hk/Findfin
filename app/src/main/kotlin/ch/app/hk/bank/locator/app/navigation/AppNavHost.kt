package ch.app.hk.bank.locator.app.navigation

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import ch.app.hk.bank.locator.feature.onboarding.navigation.LandingNavGraphDestination
import ch.app.hk.bank.locator.feature.onboarding.navigation.landingNavGraph

@Composable
fun AppNavHost(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
) {
    NavHost(
        modifier = modifier.fillMaxSize(),
        navController = navController,
        startDestination = ch.app.hk.bank.locator.feature.onboarding.navigation.LandingNavGraphDestination.navGraphId,
    ) {
        landingNavGraph(navController)
    }
}
