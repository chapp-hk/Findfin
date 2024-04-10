package ch.app.hk.bank.locator.feature.auth.navigation

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.tween
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import ch.app.hk.bank.locator.feature.auth.ui.entry.view.AuthEntryScreenRoute
import ch.app.hk.bank.locator.feature.auth.ui.login.view.AuthLogin

fun NavGraphBuilder.authNavGraph(
    navController: NavController,
    finishAuth: () -> Unit,
) {
    navigation(
        startDestination = AuthEntryDestination.route,
        route = AuthNavGraphDestination.navGraphId,
    ) {
        composable(route = AuthEntryDestination.route) {
            AuthEntryScreenRoute(
                finishAuth = finishAuth,
                goToLogin = {
                    navController.navigate(AuthLoginDestination.route)
                },
            )
        }

        composable(
            route = AuthLoginDestination.route,
            enterTransition = {
                slideIntoContainer(
                    towards = AnimatedContentTransitionScope.SlideDirection.Start,
                    animationSpec = tween(durationMillis = 300),
                )
            },
            exitTransition = {
                slideOutOfContainer(
                    towards = AnimatedContentTransitionScope.SlideDirection.End,
                    animationSpec = tween(durationMillis = 300),
                )
            },
        ) {
            AuthLogin(
                onBack = navController::navigateUp,
                onAuthorized = finishAuth,
            )
        }
    }
}
