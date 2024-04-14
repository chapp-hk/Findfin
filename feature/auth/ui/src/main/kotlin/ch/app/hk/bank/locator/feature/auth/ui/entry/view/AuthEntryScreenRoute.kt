package ch.app.hk.bank.locator.feature.auth.ui.entry.view

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import ch.app.hk.bank.locator.core.design.ui.ScreenStateView
import ch.app.hk.bank.locator.feature.auth.ui.R
import ch.app.hk.bank.locator.feature.auth.ui.entry.state.AuthEntryUiState
import ch.app.hk.bank.locator.feature.auth.ui.entry.viewmodel.AuthEntryViewModel
import ch.app.hk.bank.locator.feature.auth.ui.entry.viewmodel.AuthEntryViewModelImpl
import ch.app.hk.bank.locator.feature.auth.ui.register.view.AuthRegister

@Composable
fun AuthEntryScreenRoute(
    authEntryViewModel: AuthEntryViewModel = hiltViewModel<AuthEntryViewModelImpl>(),
    finishAuth: () -> Unit,
) = ScreenStateView(
    state = authEntryViewModel.uiState.collectAsStateWithLifecycle(),
    success = { uiState ->
        when (uiState) {
            AuthEntryUiState.AuthInitialized -> {
                authEntryViewModel.setIsAuthInitialized()
                finishAuth()
            }

            AuthEntryUiState.StartAuth -> {
                val description = stringResource(id = R.string.auth_content_description_register)
                AuthRegister(
                    modifier = Modifier.semantics { contentDescription = description },
                    onFinishAuth = {
                        authEntryViewModel.setIsAuthInitialized()
                        finishAuth()
                    },
                )
            }
        }
    },
)
