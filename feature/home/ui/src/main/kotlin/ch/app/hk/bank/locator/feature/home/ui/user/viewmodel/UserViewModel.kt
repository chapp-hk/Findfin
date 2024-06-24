package ch.app.hk.bank.locator.feature.home.ui.user.viewmodel

import ch.app.hk.bank.locator.core.design.ui.ScreenStateFlow
import ch.app.hk.bank.locator.feature.home.ui.user.state.UserUiState

interface UserViewModel {
    val uiState: ScreenStateFlow<UserUiState, Nothing>
}
