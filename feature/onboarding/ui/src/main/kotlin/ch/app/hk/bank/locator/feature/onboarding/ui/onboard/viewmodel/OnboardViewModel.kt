package ch.app.hk.bank.locator.feature.onboarding.ui.onboard.viewmodel

import kotlinx.coroutines.flow.StateFlow

interface OnboardViewModel {
    val uiState: StateFlow<OnboardUiState>
}
