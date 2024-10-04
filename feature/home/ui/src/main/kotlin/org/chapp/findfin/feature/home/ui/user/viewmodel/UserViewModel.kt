package org.chapp.findfin.feature.home.ui.user.viewmodel

import org.chapp.findfin.core.design.ui.ScreenStateFlow
import org.chapp.findfin.feature.home.ui.user.state.UserUiState

interface UserViewModel {
    val uiState: ScreenStateFlow<UserUiState, Nothing>
}
