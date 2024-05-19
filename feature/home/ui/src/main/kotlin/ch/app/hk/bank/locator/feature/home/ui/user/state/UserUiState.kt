package ch.app.hk.bank.locator.feature.home.ui.user.state

import ch.app.hk.bank.locator.feature.auth.data.repo.user.model.UserModel

sealed interface UserUiState {
    data object Guest : UserUiState

    data class Authorized(val user: UserModel) : UserUiState
}
