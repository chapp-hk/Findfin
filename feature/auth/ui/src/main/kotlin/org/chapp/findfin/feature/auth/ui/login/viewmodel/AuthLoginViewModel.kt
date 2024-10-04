package org.chapp.findfin.feature.auth.ui.login.viewmodel

import org.chapp.findfin.core.design.ui.ScreenStateFlow
import org.chapp.findfin.feature.auth.ui.login.state.LoginUiState

interface AuthLoginViewModel {
    val uiState: ScreenStateFlow<LoginUiState, LoginUiState.Error>

    fun emailPasswordLogin(
        email: String,
        password: String,
    )
}
