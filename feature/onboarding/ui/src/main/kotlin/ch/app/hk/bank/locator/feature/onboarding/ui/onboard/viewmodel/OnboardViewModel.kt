package ch.app.hk.bank.locator.feature.onboarding.ui.onboard.viewmodel

import kotlinx.coroutines.flow.SharedFlow

interface OnboardViewModel {
    val uiState: SharedFlow<OnboardUiState>
}
