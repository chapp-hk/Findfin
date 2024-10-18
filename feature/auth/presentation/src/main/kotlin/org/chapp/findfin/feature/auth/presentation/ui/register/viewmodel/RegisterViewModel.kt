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
import org.chapp.findfin.feature.auth.presentation.ui.register.state.RegisterError
import org.chapp.findfin.feature.auth.presentation.ui.register.state.RegisterUiState
import javax.inject.Inject

@HiltViewModel
internal class RegisterViewModel @Inject constructor(
    private val registerRepository: RegisterRepository,
) : ViewModel() {
    private val _uiState = MutableStateFlow<RegisterUiState>(RegisterUiState.None)
    val uiState: StateFlow<RegisterUiState> = _uiState.asStateFlow()

    fun emailPasswordRegister(
        email: String,
        password: String,
    ) {
        viewModelScope.launch {
            _uiState.emit(RegisterUiState.Loading)

            val result =
                when (registerRepository.emailPasswordRegister(email, password)) {
                    RegisterResult.Authorized ->
                        RegisterUiState.Authorized

                    RegisterResult.Error.EmailAlreadyInUse ->
                        RegisterUiState.Error(RegisterError.EMAIL_ALREADY_IN_USE)

                    RegisterResult.Error.InvalidEmail ->
                        RegisterUiState.Error(RegisterError.INVALID_EMAIL)

                    RegisterResult.Error.WeakPassword ->
                        RegisterUiState.Error(RegisterError.WEAK_PASSWORD)

                    RegisterResult.Error.Unknown ->
                        RegisterUiState.Error(RegisterError.UNKNOWN)
                }
            _uiState.emit(result)
        }
    }

    fun resetUiState() {
        viewModelScope.launch {
            _uiState.emit(RegisterUiState.None)
        }
    }
}
