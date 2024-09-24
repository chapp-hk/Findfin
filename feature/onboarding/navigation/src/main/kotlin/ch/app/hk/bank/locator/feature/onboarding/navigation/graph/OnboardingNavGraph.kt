package ch.app.hk.bank.locator.feature.onboarding.navigation.graph

import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import ch.app.hk.bank.locator.feature.onboarding.navigation.viewmodel.OnboardingNavState
import ch.app.hk.bank.locator.feature.onboarding.navigation.viewmodel.OnboardingNavViewModel
import ch.app.hk.bank.locator.feature.onboarding.ui.language.view.SelectLanguageScreen

/**
 * Adds the onboarding navigation graph to the provided [NavGraphBuilder].
 *
 * This function sets up the navigation graph for the onboarding flow,
 * including the screens for selecting a language and requesting location permissions.
 *
 * @param navController The [NavController] used for navigation.
 * @param finishOnboarding A callback to be invoked when the onboarding process is finished.
 */
fun NavGraphBuilder.onboardingNavGraph(
    navController: NavController,
    finishOnboarding: () -> Unit,
) {
    navigation<OnboardingNavGraphDestination>(
        startDestination = OnboardingSelectLanguageDestination,
    ) {
        composable<OnboardingSelectLanguageDestination> {
            val onboardingNavViewModel = hiltViewModel<OnboardingNavViewModel>()
            val navState by onboardingNavViewModel.navState.collectAsStateWithLifecycle()

            when (navState) {
                OnboardingNavState.Loading -> {
                    // no implementation
                }

                OnboardingNavState.IsFinishedOnboard -> {
                    finishOnboarding()
                }

                OnboardingNavState.NotFinishedOnboard -> {
                    SelectLanguageScreen(
                        onFinishSelectLanguage = {
                            onboardingNavViewModel.completeOnboarding()
                            finishOnboarding()
                        },
                    )
                }
            }
        }
    }
}
