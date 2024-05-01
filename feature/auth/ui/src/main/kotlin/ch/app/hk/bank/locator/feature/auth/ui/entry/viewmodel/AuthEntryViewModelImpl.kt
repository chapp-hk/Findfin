package ch.app.hk.bank.locator.feature.auth.ui.entry.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ch.app.hk.bank.locator.core.design.ui.ScreenState
import ch.app.hk.bank.locator.feature.auth.data.repo.auth.repository.AuthRepository
import ch.app.hk.bank.locator.feature.auth.ui.entry.state.AuthEntryUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
internal class AuthEntryViewModelImpl @Inject constructor(
    private val authRepository: AuthRepository,
) : ViewModel(), AuthEntryViewModel {
    override val uiState: StateFlow<ScreenState<AuthEntryUiState>> =
        flow {
            if (authRepository.isAuthInitialized()) {
                emit(ScreenState.Success(AuthEntryUiState.AuthInitialized))
            } else {
                authRepository.setAuthInitialized()
                emit(ScreenState.Success(AuthEntryUiState.StartAuth))
            }
        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(stopTimeoutMillis = 5000L),
            initialValue = ScreenState.Empty,
        )
}
