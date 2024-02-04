package ch.app.hk.bank.locator.feature.onboarding.ui.onboard.state

sealed interface OnboardUiState {
    data object StartOnboarding : OnboardUiState
    data object GoToHome : OnboardUiState
}
