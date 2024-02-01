package ch.app.hk.bank.locator.feature.onboarding.ui.onboard.viewmodel

sealed interface OnboardUiState {
    data object ShowSelectLanguage : OnboardUiState
    data object GoToHome : OnboardUiState
}
