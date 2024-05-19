package ch.app.hk.bank.locator.feature.home.ui.user.viewmodel

import ch.app.hk.bank.locator.core.design.ui.ScreenState
import ch.app.hk.bank.locator.feature.home.ui.user.state.UserUiState
import kotlinx.coroutines.flow.StateFlow

interface UserViewModel {
    val uiState: StateFlow<ScreenState<UserUiState>>
}
