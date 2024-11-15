package org.chapp.findfin.feature.home.presentation.ui.user.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.stateIn
import org.chapp.findfin.feature.auth.data.repo.user.repository.UserRepository
import org.chapp.findfin.feature.home.presentation.ui.user.state.UserUiState
import javax.inject.Inject

@HiltViewModel
internal class UserViewModelImpl @Inject constructor(
    private val userRepository: UserRepository,
) : UserViewModel, ViewModel() {
    override val uiState: StateFlow<UserUiState> =
        flow {
            emit(UserUiState.Loading)
            val currentUser = userRepository.getCurrentUser()
            if (currentUser == null) {
                emit(UserUiState.Guest)
            } else {
                emit(UserUiState.Authorized(currentUser))
            }
        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(stopTimeoutMillis = 5000L),
            initialValue = UserUiState.Loading,
        )
}
