package ch.app.hk.bank.locator.feature.onboarding.ui.permission.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ch.app.hk.bank.locator.core.design.ui.ScreenState
import ch.app.hk.bank.locator.core.preferences.api.AppPreferencesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
internal class PermissionViewModelImpl @Inject constructor(
    private val appPreferencesRepository: AppPreferencesRepository,
) : PermissionViewModel, ViewModel() {
    private val prefKeyIsAppInitialized = "pref_key_is_app_initialized"

    private val _uiState = MutableStateFlow<ScreenState<Boolean, Nothing>>(ScreenState.Empty)
    override val uiState = _uiState.asStateFlow()

    override fun completeOnboarding() {
        viewModelScope.launch {
            appPreferencesRepository.setBoolean(prefKeyIsAppInitialized, true)
            _uiState.emit(ScreenState.Success(true))
        }
    }
}
