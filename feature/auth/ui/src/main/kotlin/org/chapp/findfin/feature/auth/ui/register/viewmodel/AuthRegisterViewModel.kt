package org.chapp.findfin.feature.auth.ui.register.viewmodel

import org.chapp.findfin.core.design.ui.ScreenStateFlow
import org.chapp.findfin.feature.auth.ui.register.state.AuthRegisterUiState

interface AuthRegisterViewModel {
    val uiState: ScreenStateFlow<AuthRegisterUiState, AuthRegisterUiState.Error>

    fun emailPasswordRegister(
        email: String,
        password: String,
    )

    fun resetUiState()
}
