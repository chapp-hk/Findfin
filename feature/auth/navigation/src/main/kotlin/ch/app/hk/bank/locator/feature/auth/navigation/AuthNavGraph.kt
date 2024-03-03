package ch.app.hk.bank.locator.feature.auth.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import ch.app.hk.bank.locator.feature.auth.ui.entry.screen.AuthEntryScreen

fun NavGraphBuilder.authNavGraph(
    navController: NavController,
    finishAuth: () -> Unit,
) {
    navigation(
        startDestination = AuthEntryDestination.route,
        route = AuthNavGraphDestination.navGraphId,
    ) {
        composable(route = AuthEntryDestination.route) {
            AuthEntryScreen(finishAuth = finishAuth)
        }
    }
}
