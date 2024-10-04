package org.chapp.findfin.feature.auth.ui.login.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import org.chapp.findfin.core.design.ui.ScreenState
import org.chapp.findfin.core.design.ui.ScreenStateFlow
import org.chapp.findfin.core.design.ui.mutableScreenStateFlowOf
import org.chapp.findfin.feature.auth.data.repo.login.model.LoginResult
import org.chapp.findfin.feature.auth.data.repo.login.repository.LoginRepository
import org.chapp.findfin.feature.auth.ui.login.state.LoginError
import org.chapp.findfin.feature.auth.ui.login.state.LoginUiState
import javax.inject.Inject

@HiltViewModel
internal class AuthLoginViewModelImpl @Inject constructor(
    private val loginRepository: LoginRepository,
) : ViewModel(), AuthLoginViewModel {
    private val _uiState = mutableScreenStateFlowOf<LoginUiState, LoginUiState.Error>(ScreenState.Empty)
    override val uiState: ScreenStateFlow<LoginUiState, LoginUiState.Error> = _uiState.asStateFlow()

    override fun emailPasswordLogin(
        email: String,
        password: String,
    ) {
        viewModelScope.launch {
            _uiState.emit(ScreenState.Loading)

            val loginResult =
                loginRepository.emailPasswordLogin(
                    email = email,
                    password = password,
                )

            val state =
                when (loginResult) {
                    LoginResult.Success ->
                        ScreenState.Success<LoginUiState, Nothing>(LoginUiState.Authorized)

                    LoginResult.Error.Unknown ->
                        ScreenState.Error<Nothing, LoginUiState.Error>(
                            LoginUiState.Error(LoginError.UNKNOWN),
                        )

                    LoginResult.Error.AccountDisabled ->
                        ScreenState.Error<Nothing, LoginUiState.Error>(
                            LoginUiState.Error(LoginError.ACCOUNT_DISABLED),
                        )

                    LoginResult.Error.InvalidCredential ->
                        ScreenState.Error<Nothing, LoginUiState.Error>(
                            LoginUiState.Error(LoginError.INVALID_CREDENTIAL),
                        )

                    LoginResult.Error.TooManyRequest ->
                        ScreenState.Error<Nothing, LoginUiState.Error>(
                            LoginUiState.Error(LoginError.TOO_MANY_REQUEST),
                        )
                }

            _uiState.emit(state)
        }
    }
}
