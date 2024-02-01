package ch.app.hk.bank.locator.feature.onboarding.ui.onboard.viewmodel

sealed interface OnboardUiState {
    data object SelectLanguage : OnboardUiState
    data object NavigateToHome : OnboardUiState
}
