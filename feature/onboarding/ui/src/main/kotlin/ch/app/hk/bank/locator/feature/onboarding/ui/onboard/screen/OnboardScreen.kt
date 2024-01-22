package ch.app.hk.bank.locator.feature.onboarding.ui.onboard.screen

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import ch.app.hk.bank.locator.feature.onboarding.ui.language.screen.SelectLanguageScreen
import ch.app.hk.bank.locator.feature.onboarding.ui.onboard.viewmodel.OnboardUiState
import ch.app.hk.bank.locator.feature.onboarding.ui.onboard.viewmodel.OnboardViewModel
import ch.app.hk.bank.locator.feature.onboarding.ui.onboard.viewmodel.OnboardViewModelImpl

@Composable
fun OnboardScreen(
    onboardViewModel: OnboardViewModel = hiltViewModel<OnboardViewModelImpl>(),
    navigateToHome: () -> Unit,
) {
    when (
        onboardViewModel
            .uiState
            .collectAsStateWithLifecycle(initialValue = OnboardUiState.None)
            .value
    ) {
        OnboardUiState.NavigateToHome -> navigateToHome()

        OnboardUiState.SelectLanguage -> SelectLanguageScreen()

        OnboardUiState.None -> Unit
    }
}
