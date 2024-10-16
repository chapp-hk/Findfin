package org.chapp.findfin.feature.auth.presentation.ui.register.state

sealed interface AuthRegisterUiState {
    data object None : AuthRegisterUiState

    data object Loading : AuthRegisterUiState

    data object Authorized : AuthRegisterUiState

    data class Error(val reason: AuthRegisterError) : AuthRegisterUiState
}
