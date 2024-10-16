package org.chapp.findfin.feature.auth.presentation.ui.login.state

sealed interface LoginUiState {
    data object None : LoginUiState

    data object Loading : LoginUiState

    data object Authorized : LoginUiState

    data class Error(val reason: LoginError) : LoginUiState
}
