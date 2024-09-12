package ch.app.hk.bank.locator.feature.auth.navigation.graph

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.tween
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import ch.app.hk.bank.locator.feature.auth.navigation.viewmodel.AuthNavState
import ch.app.hk.bank.locator.feature.auth.navigation.viewmodel.AuthNavViewModel
import ch.app.hk.bank.locator.feature.auth.ui.login.view.AuthLogin
import ch.app.hk.bank.locator.feature.auth.ui.register.view.AuthRegister

/**
 * Adds the authentication onboarding navigation graph to the provided [NavGraphBuilder].
 *
 * @param navController The [NavController] used for navigation.
 * @param onFinishAuthOnboarding A callback invoked when the authentication onboarding is finished.
 */
fun NavGraphBuilder.authOnboardingNavGraph(
    navController: NavController,
    onFinishAuthOnboarding: () -> Unit,
) {
    navigation<AuthOnboardingNavGraphDestination>(
        startDestination = AuthRegisterDestination,
    ) {
        composable<AuthRegisterDestination> {
            val authNavViewModel = hiltViewModel<AuthNavViewModel>()
            val navState by authNavViewModel.navState.collectAsStateWithLifecycle()

            when (navState) {
                AuthNavState.Loading -> {
                    // no implementation
                }

                AuthNavState.IsInitialized -> {
                    onFinishAuthOnboarding()
                }

                AuthNavState.NotInitialized -> {
                    AuthRegister(
                        onClose = {
                            authNavViewModel.setAuthInitialized()
                            onFinishAuthOnboarding()
                        },
                        onFinishAuth = {
                            authNavViewModel.setAuthInitialized()
                            onFinishAuthOnboarding()
                        },
                        onHaveAccount = {
                            navController.navigate(AuthLoginDestination)
                        },
                    )
                }
            }
        }

        composable<AuthLoginDestination> {
            val authNavViewModel = hiltViewModel<AuthNavViewModel>()

            AuthLogin(
                onClose = {
                    authNavViewModel.setAuthInitialized()
                    navController.navigateUp()
                },
                onFinishAuth = {
                    authNavViewModel.setAuthInitialized()
                    onFinishAuthOnboarding()
                },
                onDontHaveAccount = navController::navigateUp,
            )
        }
    }
}

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
