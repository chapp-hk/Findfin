package ch.app.hk.bank.locator.feature.auth.navigation

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.tween
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import ch.app.hk.bank.locator.feature.auth.ui.entry.view.AuthEntryScreenRoute
import ch.app.hk.bank.locator.feature.auth.ui.login.view.AuthLogin
import ch.app.hk.bank.locator.feature.auth.ui.register.view.AuthRegister

fun NavGraphBuilder.authNavGraph(
    navController: NavController,
    finishAuth: () -> Unit,
) {
    navigation<AuthNavGraphDestination>(
        startDestination = AuthEntryDestination,
    ) {
        composable<AuthEntryDestination>(
            enterTransition = {
                slideIntoContainer(
                    towards = AnimatedContentTransitionScope.SlideDirection.Up,
                    animationSpec = tween(durationMillis = 700),
                )
            },
            exitTransition = null,
            popEnterTransition = null,
            popExitTransition = {
                slideOutOfContainer(
                    towards = AnimatedContentTransitionScope.SlideDirection.Down,
                    animationSpec = tween(durationMillis = 700),
                )
            },
        ) {
            AuthEntryScreenRoute(
                finishAuth = finishAuth,
                startAuth = {
                    AuthRegister(
                        onClose = finishAuth,
                        onFinishAuth = finishAuth,
                        onHaveAccount = {
                            navController.navigate(AuthLoginDestination)
                        },
                    )
                },
            )
        }

        composable<AuthLoginDestination> {
            AuthLogin(
                onClose = navController::navigateUp,
                onAuthorized = finishAuth,
                onDontHaveAccount = navController::navigateUp,
            )
        }
    }
}
