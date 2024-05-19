package ch.app.hk.bank.locator.feature.home.ui.user.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ch.app.hk.bank.locator.core.design.ui.ScreenState
import ch.app.hk.bank.locator.feature.auth.data.repo.user.repository.UserRepository
import ch.app.hk.bank.locator.feature.home.ui.user.state.UserUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
internal class UserViewModelImpl @Inject constructor(
    private val userRepository: UserRepository,
) : UserViewModel, ViewModel() {
    override val uiState: StateFlow<ScreenState<UserUiState>> =
        flow {
            val currentUser = userRepository.getCurrentUser()
            if (currentUser == null) {
                emit(ScreenState.Success(UserUiState.Guest))
            } else {
                emit(ScreenState.Success(UserUiState.Authorized(currentUser)))
            }
        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(stopTimeoutMillis = 5000L),
            initialValue = ScreenState.Empty,
        )
}
