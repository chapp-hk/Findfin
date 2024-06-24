package ch.app.hk.bank.locator.feature.onboarding.ui.permission.viewmodel

import ch.app.hk.bank.locator.core.design.ui.ScreenState
import kotlinx.coroutines.flow.StateFlow

interface PermissionViewModel {
    fun completeOnboarding()

    val uiState: StateFlow<ScreenState<Boolean, Nothing>>
}
