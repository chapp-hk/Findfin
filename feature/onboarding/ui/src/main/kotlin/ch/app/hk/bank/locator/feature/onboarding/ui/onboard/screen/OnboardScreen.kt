package ch.app.hk.bank.locator.feature.onboarding.ui.onboard.screen

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import ch.app.hk.bank.locator.core.design.ui.ScreenStateView
import ch.app.hk.bank.locator.feature.onboarding.ui.language.screen.SelectLanguageScreen
import ch.app.hk.bank.locator.feature.onboarding.ui.onboard.viewmodel.OnboardUiState
import ch.app.hk.bank.locator.feature.onboarding.ui.onboard.viewmodel.OnboardViewModel
import ch.app.hk.bank.locator.feature.onboarding.ui.onboard.viewmodel.OnboardViewModelImpl

@Composable
fun OnboardScreen(
    onboardViewModel: OnboardViewModel = hiltViewModel<OnboardViewModelImpl>(),
    showSelectLanguage: @Composable () -> Unit = { SelectLanguageScreen() },
    goToHome: () -> Unit,
) {
    ScreenStateView(
        state = onboardViewModel.uiState.collectAsStateWithLifecycle().value,
        success = { onboardUiState ->
            when (onboardUiState) {
                OnboardUiState.GoToHome -> goToHome()
                OnboardUiState.ShowSelectLanguage -> showSelectLanguage()
            }
        }
    )
}
