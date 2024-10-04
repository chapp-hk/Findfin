package org.chapp.findfin.feature.auth.ui.register.state

sealed interface AuthRegisterUiState {
    data object Authorized : AuthRegisterUiState

    data class Error(val reason: AuthRegisterError) : AuthRegisterUiState
}
