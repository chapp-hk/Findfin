package ch.app.hk.bank.locator.feature.auth.ui.entry.viewmodel

import ch.app.hk.bank.locator.core.design.ui.ScreenStateFlow
import ch.app.hk.bank.locator.feature.auth.ui.entry.state.AuthEntryUiState

interface AuthEntryViewModel {
    val uiState: ScreenStateFlow<AuthEntryUiState, Nothing>
}
