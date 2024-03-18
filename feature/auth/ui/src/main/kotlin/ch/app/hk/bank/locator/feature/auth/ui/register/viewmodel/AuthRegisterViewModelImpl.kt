package ch.app.hk.bank.locator.feature.auth.ui.register.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ch.app.hk.bank.locator.core.design.ui.ScreenState
import ch.app.hk.bank.locator.feature.auth.data.repo.model.AuthResult
import ch.app.hk.bank.locator.feature.auth.data.repo.repository.AuthRepository
import ch.app.hk.bank.locator.feature.auth.ui.register.state.AuthRegisterError
import ch.app.hk.bank.locator.feature.auth.ui.register.state.AuthRegisterUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthRegisterViewModelImpl
    @Inject
    constructor(
        private val authRepository: AuthRepository,
    ) : ViewModel(), AuthRegisterViewModel {
        private val _uiState = MutableStateFlow<ScreenState<AuthRegisterUiState>>(ScreenState.Empty)
        override val uiState: StateFlow<ScreenState<AuthRegisterUiState>> = _uiState.asStateFlow()

        override fun anonymousLogin() {
            viewModelScope.launch {
                _uiState.emit(ScreenState.Loading)

                val state =
                    when (authRepository.anonymousLogin()) {
                        AuthResult.Authorized ->
                            ScreenState.Success(AuthRegisterUiState.Authorized)

                        is AuthResult.Error ->
                            ScreenState.Error(AuthRegisterUiState.Error(AuthRegisterError.UNKNOWN))
                    }
                _uiState.emit(state)
            }
        }

        override fun emailPasswordRegister(
            email: String,
            password: String,
        ) {
            viewModelScope.launch {
                _uiState.emit(ScreenState.Loading)

                val result =
                    when (authRepository.emailPasswordRegister(email, password)) {
                        AuthResult.Authorized ->
                            ScreenState.Success(AuthRegisterUiState.Authorized)

                        AuthResult.Error.Register.EmailAlreadyInUse ->
                            ScreenState.Error(AuthRegisterUiState.Error(AuthRegisterError.EMAIL_ALREADY_IN_USE))

                        AuthResult.Error.Register.InvalidEmail ->
                            ScreenState.Error(AuthRegisterUiState.Error(AuthRegisterError.INVALID_EMAIL))

                        AuthResult.Error.Register.WeakPassword ->
                            ScreenState.Error(AuthRegisterUiState.Error(AuthRegisterError.WEAK_PASSWORD))

                        AuthResult.Error.Unknown ->
                            ScreenState.Error(AuthRegisterUiState.Error(AuthRegisterError.UNKNOWN))
                    }
                _uiState.emit(result)
            }
        }

        override fun resetUiState() {
            viewModelScope.launch {
                _uiState.emit(ScreenState.Empty)
            }
        }
    }
