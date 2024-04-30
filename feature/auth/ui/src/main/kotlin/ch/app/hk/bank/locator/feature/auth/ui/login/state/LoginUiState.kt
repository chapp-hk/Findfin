package ch.app.hk.bank.locator.feature.auth.ui.login.state

sealed interface LoginUiState {
    data object Authorized : LoginUiState

    data class Error(val reason: LoginError) : LoginUiState
}
