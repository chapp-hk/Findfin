package ch.app.hk.bank.locator.feature.home.ui.user.view

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import ch.app.hk.bank.locator.core.design.ui.ScreenStateView
import ch.app.hk.bank.locator.feature.home.ui.user.state.UserUiState
import ch.app.hk.bank.locator.feature.home.ui.user.viewmodel.UserViewModel
import ch.app.hk.bank.locator.feature.home.ui.user.viewmodel.UserViewModelImpl

@Composable
internal fun UserStatus(
    modifier: Modifier = Modifier,
    userViewModel: UserViewModel = hiltViewModel<UserViewModelImpl>(),
    onRequestAuth: () -> Unit,
) {
    ScreenStateView(
        state = userViewModel.uiState.collectAsStateWithLifecycle(),
        success = { uiState ->
            when (uiState) {
                is UserUiState.Authorized -> {
                    Authorized(
                        modifier = modifier,
                        uiState.user,
                    )
                }

                UserUiState.Guest -> {
                    Guest(
                        modifier = modifier,
                        onRequestAuth = onRequestAuth,
                    )
                }
            }
        },
    )
}
