package org.chapp.findfin.feature.auth.navigation.graph

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.tween
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import org.chapp.findfin.feature.auth.ui.login.view.AuthLogin
import org.chapp.findfin.feature.auth.ui.register.view.AuthRegister

/**
 * Adds the main authentication navigation graph to the provided [NavGraphBuilder].
 *
 * @param navController The [NavController] used for navigation.
 * @param finishAuth A callback invoked when the authentication process is finished.
 */
fun NavGraphBuilder.authNavGraph(
    navController: NavController,
    finishAuth: () -> Unit,
) {
    navigation<AuthNavGraphDestination>(
        startDestination = AuthLoginDestination,
    ) {
        composable<AuthLoginDestination>(
            enterTransition = {
                slideIntoContainer(
                    towards = AnimatedContentTransitionScope.SlideDirection.Up,
                    animationSpec = tween(durationMillis = 500),
                )
            },
            exitTransition = null,
            popEnterTransition = null,
            popExitTransition = {
                slideOutOfContainer(
                    towards = AnimatedContentTransitionScope.SlideDirection.Down,
                    animationSpec = tween(durationMillis = 500),
                )
            },
        ) {
            AuthLogin(
                onClose = navController::navigateUp,
                onFinishAuth = finishAuth,
                onDontHaveAccount = {
                    navController.navigate(AuthRegisterDestination)
                },
            )
        }

        composable<AuthRegisterDestination> {
            AuthRegister(
                onClose = finishAuth,
                onFinishAuth = finishAuth,
                onHaveAccount = navController::navigateUp,
            )
        }
    }
}
