package ch.app.hk.bank.locator.feature.auth.ui.entry.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ch.app.hk.bank.locator.core.ui.ScreenState
import ch.app.hk.bank.locator.feature.auth.data.repo.repository.AuthRepository
import ch.app.hk.bank.locator.feature.auth.ui.entry.state.AuthEntryUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
internal class AuthEntryViewModelImpl
    @Inject
    constructor(
        authRepository: AuthRepository,
    ) : ViewModel(), AuthEntryViewModel {
        override val uiState: StateFlow<ScreenState<AuthEntryUiState>> =
            flow {
                val screenState =
                    if (authRepository.isAuthorized()) {
                        AuthEntryUiState.Authorized
                    } else {
                        AuthEntryUiState.StartAuth
                    }

                emit(ScreenState.Success(screenState))
            }.stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(stopTimeoutMillis = 5000L),
                initialValue = ScreenState.Empty,
            )
    }
