package ch.app.hk.bank.locator.feature.onboarding.ui.onboard.view

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import ch.app.hk.bank.locator.core.design.ui.ScreenStateView
import ch.app.hk.bank.locator.feature.onboarding.ui.language.view.SelectLanguageScreen
import ch.app.hk.bank.locator.feature.onboarding.ui.onboard.state.OnboardUiState
import ch.app.hk.bank.locator.feature.onboarding.ui.onboard.viewmodel.OnboardViewModel
import ch.app.hk.bank.locator.feature.onboarding.ui.onboard.viewmodel.OnboardViewModelImpl

@Composable
fun OnboardScreen(
    onboardViewModel: OnboardViewModel = hiltViewModel<OnboardViewModelImpl>(),
    finishOnboarding: () -> Unit,
    goToRequestPermission: () -> Unit,
) {
    val state = onboardViewModel.uiState.collectAsStateWithLifecycle()
    ScreenStateView(
        state = state,
        success = { onboardUiState ->
            when (onboardUiState) {
                OnboardUiState.GoToHome -> finishOnboarding()
                OnboardUiState.StartOnboarding -> {
                    SelectLanguageScreen(
                        modifier = Modifier.testTag(TEST_TAG_ONBOARDING_SELECT_LANGUAGE_SCREEN),
                        goToRequestPermission = goToRequestPermission,
                    )
                }
            }
        },
    )
}

internal const val TEST_TAG_ONBOARDING_SELECT_LANGUAGE_SCREEN =
    "onboarding_select_language_screen"
