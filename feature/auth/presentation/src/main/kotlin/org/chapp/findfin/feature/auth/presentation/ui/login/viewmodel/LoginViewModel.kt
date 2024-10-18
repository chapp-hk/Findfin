package org.chapp.findfin.feature.auth.presentation.ui.login.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import org.chapp.findfin.feature.auth.data.repo.login.model.LoginResult
import org.chapp.findfin.feature.auth.data.repo.login.repository.LoginRepository
import org.chapp.findfin.feature.auth.presentation.ui.login.state.LoginError
import org.chapp.findfin.feature.auth.presentation.ui.login.state.LoginUiState
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val loginRepository: LoginRepository,
) : ViewModel() {
    private val _uiState = MutableStateFlow<LoginUiState>(LoginUiState.None)
    val uiState: StateFlow<LoginUiState> = _uiState.asStateFlow()

    fun emailPasswordLogin(
        email: String,
        password: String,
    ) {
        viewModelScope.launch {
            _uiState.emit(LoginUiState.Loading)

            val loginResult =
                loginRepository.emailPasswordLogin(
                    email = email,
                    password = password,
                )

            val state =
                when (loginResult) {
                    LoginResult.Success ->
                        LoginUiState.Authorized

                    LoginResult.Error.Unknown ->
                        LoginUiState.Error(LoginError.UNKNOWN)

                    LoginResult.Error.AccountDisabled ->
                        LoginUiState.Error(LoginError.ACCOUNT_DISABLED)

                    LoginResult.Error.InvalidCredential ->
                        LoginUiState.Error(LoginError.INVALID_CREDENTIAL)

                    LoginResult.Error.TooManyRequest ->
                        LoginUiState.Error(LoginError.TOO_MANY_REQUEST)
                }

            _uiState.emit(state)
        }
    }
}
