package org.chapp.findfin.feature.home.presentation.ui.user.state

import org.chapp.findfin.feature.auth.data.repo.user.model.UserModel

internal sealed interface UserUiState {
    data object Guest : UserUiState

    data class Authorized(val user: UserModel) : UserUiState
}
