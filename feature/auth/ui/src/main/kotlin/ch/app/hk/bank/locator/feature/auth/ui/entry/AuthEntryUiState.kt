package ch.app.hk.bank.locator.feature.auth.ui.entry

sealed interface AuthEntryUiState {
    data object Authorized : AuthEntryUiState

    data object ShowAuthIntroduction : AuthEntryUiState
}
