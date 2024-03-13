package ch.app.hk.bank.locator.feature.auth.ui.register.viewmodel

import ch.app.hk.bank.locator.core.ui.ScreenState
import ch.app.hk.bank.locator.feature.auth.ui.register.state.AuthRegisterUiState
import kotlinx.coroutines.flow.StateFlow

interface AuthRegisterViewModel {
    val uiState: StateFlow<ScreenState<AuthRegisterUiState>>

    fun anonymousLogin()
}
