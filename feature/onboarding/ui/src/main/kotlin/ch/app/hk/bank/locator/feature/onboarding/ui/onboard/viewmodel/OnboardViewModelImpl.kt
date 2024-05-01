package ch.app.hk.bank.locator.feature.onboarding.ui.onboard.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ch.app.hk.bank.locator.core.design.ui.ScreenState
import ch.app.hk.bank.locator.core.preferences.api.AppPreferencesRepository
import ch.app.hk.bank.locator.feature.onboarding.ui.onboard.state.OnboardUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class OnboardViewModelImpl @Inject constructor(
    appPreferencesRepository: AppPreferencesRepository,
) : ViewModel(), OnboardViewModel {
    private val prefKeyIsAppInitialized = "pref_key_is_app_initialized"

    override val uiState: StateFlow<ScreenState<OnboardUiState>> =
        appPreferencesRepository
            .getBoolean(key = prefKeyIsAppInitialized)
            .map { isAppInitialized ->
                val onboardUiState =
                    if (isAppInitialized) {
                        OnboardUiState.GoToHome
                    } else {
                        OnboardUiState.StartOnboarding
                    }

                ScreenState.Success(onboardUiState)
            }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(stopTimeoutMillis = 5000L),
                initialValue = ScreenState.Loading,
            )
}
