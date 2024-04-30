package ch.app.hk.bank.locator.feature.auth.ui.login.viewmodel

import ch.app.hk.bank.locator.core.design.ui.ScreenState
import ch.app.hk.bank.locator.feature.auth.ui.login.state.LoginUiState
import kotlinx.coroutines.flow.StateFlow

interface AuthLoginViewModel {
    val uiState: StateFlow<ScreenState<LoginUiState>>

    fun emailPasswordLogin(
        email: String,
        password: String,
    )
}
