package ch.app.hk.bank.locator.feature.auth.ui.login.viewmodel

import ch.app.hk.bank.locator.core.design.ui.ScreenStateFlow
import ch.app.hk.bank.locator.feature.auth.ui.login.state.LoginUiState

interface AuthLoginViewModel {
    val uiState: ScreenStateFlow<LoginUiState, LoginUiState.Error>

    fun emailPasswordLogin(
        email: String,
        password: String,
    )
}
