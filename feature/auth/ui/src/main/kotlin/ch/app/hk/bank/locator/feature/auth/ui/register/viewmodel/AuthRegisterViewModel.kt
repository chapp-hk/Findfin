package ch.app.hk.bank.locator.feature.auth.ui.register.viewmodel

import ch.app.hk.bank.locator.core.design.ui.ScreenStateFlow
import ch.app.hk.bank.locator.feature.auth.ui.register.state.AuthRegisterUiState

interface AuthRegisterViewModel {
    val uiState: ScreenStateFlow<AuthRegisterUiState, AuthRegisterUiState.Error>

    fun emailPasswordRegister(
        email: String,
        password: String,
    )

    fun resetUiState()
}
