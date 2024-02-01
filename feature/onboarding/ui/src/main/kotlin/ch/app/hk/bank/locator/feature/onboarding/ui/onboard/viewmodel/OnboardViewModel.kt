package ch.app.hk.bank.locator.feature.onboarding.ui.onboard.viewmodel

import ch.app.hk.bank.locator.core.design.ui.ScreenState
import kotlinx.coroutines.flow.StateFlow

interface OnboardViewModel {
    val uiState: StateFlow<ScreenState<OnboardUiState>>
}
