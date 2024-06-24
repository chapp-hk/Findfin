package ch.app.hk.bank.locator.feature.onboarding.ui.permission.viewmodel

import ch.app.hk.bank.locator.core.design.ui.ScreenStateFlow

interface PermissionViewModel {
    fun completeOnboarding()

    val uiState: ScreenStateFlow<Boolean, Nothing>
}
