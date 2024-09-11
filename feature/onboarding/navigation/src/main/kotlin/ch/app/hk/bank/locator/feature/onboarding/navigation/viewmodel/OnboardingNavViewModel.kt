package ch.app.hk.bank.locator.feature.onboarding.navigation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ch.app.hk.bank.locator.core.preferences.api.AppPreferencesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class OnboardingNavViewModel @Inject constructor(
    private val appPreferencesRepository: AppPreferencesRepository,
) : ViewModel() {
    private val prefKeyIsFinishedOnboard = "onboarding_pref_key_is_finished_onboard"

    val isFinishedOnboard: StateFlow<Boolean> =
        appPreferencesRepository
            .getBoolean(key = prefKeyIsFinishedOnboard)
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(),
                initialValue = false,
            )

    fun completeOnboarding() {
        viewModelScope.launch {
            appPreferencesRepository.setBoolean(key = prefKeyIsFinishedOnboard, value = true)
        }
    }
}
