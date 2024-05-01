package ch.app.hk.bank.locator.feature.auth.ui.login.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ch.app.hk.bank.locator.core.design.ui.ScreenState
import ch.app.hk.bank.locator.feature.auth.data.repo.login.model.LoginResult
import ch.app.hk.bank.locator.feature.auth.data.repo.login.repository.LoginRepositoryImpl
import ch.app.hk.bank.locator.feature.auth.ui.login.state.LoginError
import ch.app.hk.bank.locator.feature.auth.ui.login.state.LoginUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
internal class AuthLoginViewModelImpl @Inject constructor(
    private val loginRepository: LoginRepositoryImpl,
) : ViewModel(), AuthLoginViewModel {
    private val _uiState = MutableStateFlow<ScreenState<LoginUiState>>(ScreenState.Empty)
    override val uiState: StateFlow<ScreenState<LoginUiState>> = _uiState.asStateFlow()

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
                        ScreenState.Success(LoginUiState.Authorized)

                    LoginResult.Error.Unknown ->
                        ScreenState.Error(LoginUiState.Error(LoginError.UNKNOWN))

                    LoginResult.Error.AccountDisabled ->
                        ScreenState.Error(LoginUiState.Error(LoginError.ACCOUNT_DISABLED))

                    LoginResult.Error.InvalidCredential ->
                        ScreenState.Error(LoginUiState.Error(LoginError.INVALID_CREDENTIAL))

                    LoginResult.Error.TooManyRequest ->
                        ScreenState.Error(LoginUiState.Error(LoginError.TOO_MANY_REQUEST))
                }

            _uiState.emit(state)
        }
    }
}
