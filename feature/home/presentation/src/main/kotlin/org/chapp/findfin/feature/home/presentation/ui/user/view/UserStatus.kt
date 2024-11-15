package org.chapp.findfin.feature.home.presentation.ui.user.view

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import org.chapp.findfin.feature.home.presentation.ui.user.state.UserUiState
import org.chapp.findfin.feature.home.presentation.ui.user.viewmodel.UserViewModel
import org.chapp.findfin.feature.home.presentation.ui.user.viewmodel.UserViewModelImpl

@Composable
internal fun UserStatus(
    modifier: Modifier = Modifier,
    userViewModel: UserViewModel = hiltViewModel<UserViewModelImpl>(),
    onRequestAuth: () -> Unit,
) {
    val uiState by userViewModel.uiState.collectAsStateWithLifecycle()
    when (uiState) {
        UserUiState.Loading -> {
            Loading()
        }

        is UserUiState.Authorized -> {
            Authorized(
                modifier = modifier,
                user = (uiState as UserUiState.Authorized).user,
            )
        }

        UserUiState.Guest -> {
            Guest(
                modifier = modifier,
                onRequestAuth = onRequestAuth,
            )
        }
    }
}
