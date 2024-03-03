package ch.app.hk.bank.locator.feature.auth.ui.entry.screen

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import ch.app.hk.bank.locator.core.design.ui.ScreenStateView
import ch.app.hk.bank.locator.feature.auth.ui.entry.AuthEntryUiState
import ch.app.hk.bank.locator.feature.auth.ui.entry.viewmodel.AuthEntryViewModel
import ch.app.hk.bank.locator.feature.auth.ui.entry.viewmodel.AuthEntryViewModelImpl
import ch.app.hk.bank.locator.feature.auth.ui.introduction.screen.AuthIntroductionScreen

@Composable
fun AuthEntryScreen(
    authEntryViewModel: AuthEntryViewModel = hiltViewModel<AuthEntryViewModelImpl>(),
    finishAuth: () -> Unit,
) {
    ScreenStateView(
        state = authEntryViewModel.uiState.collectAsStateWithLifecycle().value,
        success = { uiState ->
            when (uiState) {
                AuthEntryUiState.Authorized -> {
                    finishAuth()
                }
                AuthEntryUiState.ShowAuthIntroduction -> {
                    AuthIntroductionScreen()
                }
            }
        },
    )
}
