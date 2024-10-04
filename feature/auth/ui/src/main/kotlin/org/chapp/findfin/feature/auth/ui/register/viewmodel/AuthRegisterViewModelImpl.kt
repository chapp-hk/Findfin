package org.chapp.findfin.feature.auth.ui.register.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import org.chapp.findfin.core.design.ui.ScreenState
import org.chapp.findfin.core.design.ui.ScreenStateFlow
import org.chapp.findfin.core.design.ui.mutableScreenStateFlowOf
import org.chapp.findfin.feature.auth.data.repo.register.model.RegisterResult
import org.chapp.findfin.feature.auth.data.repo.register.repository.RegisterRepository
import org.chapp.findfin.feature.auth.ui.register.state.AuthRegisterError
import org.chapp.findfin.feature.auth.ui.register.state.AuthRegisterUiState
import javax.inject.Inject

@HiltViewModel
class AuthRegisterViewModelImpl @Inject constructor(
    private val registerRepository: RegisterRepository,
) : ViewModel(), AuthRegisterViewModel {
    private val _uiState = mutableScreenStateFlowOf<AuthRegisterUiState, AuthRegisterUiState.Error>(ScreenState.Empty)
    override val uiState: ScreenStateFlow<AuthRegisterUiState, AuthRegisterUiState.Error> = _uiState.asStateFlow()

    override fun emailPasswordRegister(
        email: String,
        password: String,
    ) {
        viewModelScope.launch {
            _uiState.emit(ScreenState.Loading)

            val result =
                when (registerRepository.emailPasswordRegister(email, password)) {
                    RegisterResult.Authorized ->
                        ScreenState.Success<AuthRegisterUiState, AuthRegisterUiState.Error>(
                            AuthRegisterUiState.Authorized,
                        )

                    RegisterResult.Error.Register.EmailAlreadyInUse ->
                        ScreenState.Error(
                            AuthRegisterUiState.Error(AuthRegisterError.EMAIL_ALREADY_IN_USE),
                        )

                    RegisterResult.Error.Register.InvalidEmail ->
                        ScreenState.Error(
                            AuthRegisterUiState.Error(AuthRegisterError.INVALID_EMAIL),
                        )

                    RegisterResult.Error.Register.WeakPassword ->
                        ScreenState.Error(
                            AuthRegisterUiState.Error(AuthRegisterError.WEAK_PASSWORD),
                        )

                    RegisterResult.Error.Unknown ->
                        ScreenState.Error(
                            AuthRegisterUiState.Error(AuthRegisterError.UNKNOWN),
                        )
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
