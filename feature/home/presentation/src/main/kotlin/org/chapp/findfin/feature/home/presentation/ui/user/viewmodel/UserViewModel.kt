package org.chapp.findfin.feature.home.presentation.ui.user.viewmodel

import kotlinx.coroutines.flow.StateFlow
import org.chapp.findfin.feature.home.presentation.ui.user.state.UserUiState

internal interface UserViewModel {
    val uiState: StateFlow<UserUiState>
}
