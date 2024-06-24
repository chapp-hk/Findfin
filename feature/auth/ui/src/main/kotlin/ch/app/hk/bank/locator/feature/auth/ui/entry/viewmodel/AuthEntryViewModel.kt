package ch.app.hk.bank.locator.feature.auth.ui.entry.viewmodel

import ch.app.hk.bank.locator.core.design.ui.ScreenState
import ch.app.hk.bank.locator.feature.auth.ui.entry.state.AuthEntryUiState
import kotlinx.coroutines.flow.StateFlow

interface AuthEntryViewModel {
    val uiState: StateFlow<ScreenState<AuthEntryUiState, Nothing>>
}
