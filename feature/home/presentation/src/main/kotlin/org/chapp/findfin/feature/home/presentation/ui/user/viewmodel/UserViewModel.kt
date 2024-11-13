package org.chapp.findfin.feature.home.presentation.ui.user.viewmodel

import org.chapp.findfin.core.design.ui.foundation.ScreenStateFlow
import org.chapp.findfin.feature.home.presentation.ui.user.state.UserUiState

interface UserViewModel {
    val uiState: ScreenStateFlow<UserUiState, Nothing>
}
