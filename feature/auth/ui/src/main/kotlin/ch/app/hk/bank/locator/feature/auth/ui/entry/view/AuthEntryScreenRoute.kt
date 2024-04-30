package ch.app.hk.bank.locator.feature.auth.ui.entry.view

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import ch.app.hk.bank.locator.core.design.ui.ScreenStateView
import ch.app.hk.bank.locator.feature.auth.ui.entry.state.AuthEntryUiState
import ch.app.hk.bank.locator.feature.auth.ui.entry.viewmodel.AuthEntryViewModel
import ch.app.hk.bank.locator.feature.auth.ui.entry.viewmodel.AuthEntryViewModelImpl

@Composable
fun AuthEntryScreenRoute(
    authEntryViewModel: AuthEntryViewModel = hiltViewModel<AuthEntryViewModelImpl>(),
    finishAuth: () -> Unit,
    startAuth: @Composable () -> Unit,
) = ScreenStateView(
    state = authEntryViewModel.uiState.collectAsStateWithLifecycle(),
    success = { uiState ->
        when (uiState) {
            AuthEntryUiState.AuthInitialized -> finishAuth()

            AuthEntryUiState.StartAuth -> {
                startAuth()
            }
        }
    },
)
