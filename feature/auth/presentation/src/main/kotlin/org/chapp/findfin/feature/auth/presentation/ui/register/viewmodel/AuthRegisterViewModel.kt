package org.chapp.findfin.feature.auth.presentation.ui.register.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import org.chapp.findfin.feature.auth.data.repo.register.model.RegisterResult
import org.chapp.findfin.feature.auth.data.repo.register.repository.RegisterRepository
import org.chapp.findfin.feature.auth.presentation.ui.register.state.AuthRegisterError
import org.chapp.findfin.feature.auth.presentation.ui.register.state.AuthRegisterUiState
import javax.inject.Inject

@HiltViewModel
class AuthRegisterViewModel @Inject constructor(
    private val registerRepository: RegisterRepository,
) : ViewModel() {
    private val _uiState = MutableStateFlow<AuthRegisterUiState>(AuthRegisterUiState.None)
    val uiState: StateFlow<AuthRegisterUiState> = _uiState.asStateFlow()

    fun emailPasswordRegister(
        email: String,
        password: String,
    ) {
        viewModelScope.launch {
            _uiState.emit(AuthRegisterUiState.Loading)

            val result =
                when (registerRepository.emailPasswordRegister(email, password)) {
                    RegisterResult.Authorized ->
                        AuthRegisterUiState.Authorized

                    RegisterResult.Error.Register.EmailAlreadyInUse ->
                        AuthRegisterUiState.Error(AuthRegisterError.EMAIL_ALREADY_IN_USE)

                    RegisterResult.Error.Register.InvalidEmail ->
                        AuthRegisterUiState.Error(AuthRegisterError.INVALID_EMAIL)

                    RegisterResult.Error.Register.WeakPassword ->
                        AuthRegisterUiState.Error(AuthRegisterError.WEAK_PASSWORD)

                    RegisterResult.Error.Unknown ->
                        AuthRegisterUiState.Error(AuthRegisterError.UNKNOWN)
                }
            _uiState.emit(result)
        }
    }

    fun resetUiState() {
        viewModelScope.launch {
            _uiState.emit(AuthRegisterUiState.None)
        }
    }
}
