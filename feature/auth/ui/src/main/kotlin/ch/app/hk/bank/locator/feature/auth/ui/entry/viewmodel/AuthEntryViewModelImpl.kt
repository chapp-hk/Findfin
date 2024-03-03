package ch.app.hk.bank.locator.feature.auth.ui.entry.viewmodel

import androidx.lifecycle.ViewModel
import ch.app.hk.bank.locator.core.design.ui.ScreenState
import ch.app.hk.bank.locator.feature.auth.ui.entry.AuthEntryUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class AuthEntryViewModelImpl
    @Inject
    constructor() : ViewModel(), AuthEntryViewModel {
        override val uiState: StateFlow<ScreenState<AuthEntryUiState>> =
            MutableStateFlow(ScreenState.Success(AuthEntryUiState.ShowAuthIntroduction))
    }
