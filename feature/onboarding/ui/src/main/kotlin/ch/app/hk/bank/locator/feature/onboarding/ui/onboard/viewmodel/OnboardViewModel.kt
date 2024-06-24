package ch.app.hk.bank.locator.feature.onboarding.ui.onboard.viewmodel

import ch.app.hk.bank.locator.core.design.ui.ScreenStateFlow
import ch.app.hk.bank.locator.feature.onboarding.ui.onboard.state.OnboardUiState

interface OnboardViewModel {
    val uiState: ScreenStateFlow<OnboardUiState, Nothing>
}
