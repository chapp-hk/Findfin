package ch.app.hk.bank.locator.feature.auth.ui.entry.state

sealed interface AuthEntryUiState {
    data object Authorized : AuthEntryUiState

    data object StartAuth : AuthEntryUiState
}
