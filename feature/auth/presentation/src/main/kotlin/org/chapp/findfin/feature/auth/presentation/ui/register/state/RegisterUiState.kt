package org.chapp.findfin.feature.auth.presentation.ui.register.state

internal sealed interface RegisterUiState {
    data object None : RegisterUiState

    data object Loading : RegisterUiState

    data object Authorized : RegisterUiState

    data class Error(val reason: RegisterError) : RegisterUiState
}
