package org.chapp.findfin.feature.onboarding.presentation.navigation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import org.chapp.findfin.core.preferences.provider.AppPreferencesManager
import javax.inject.Inject

@HiltViewModel
internal class OnboardingNavViewModel @Inject constructor(
    private val appPreferencesManager: AppPreferencesManager,
) : ViewModel() {
    private val prefKeyIsFinishedOnboard = "onboarding_pref_key_is_finished_onboard"

    val navState: StateFlow<OnboardingNavState> =
        appPreferencesManager
            .getBoolean(key = prefKeyIsFinishedOnboard)
            .map {
                if (it) {
                    OnboardingNavState.IsFinishedOnboard
                } else {
                    OnboardingNavState.NotFinishedOnboard
                }
            }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(),
                initialValue = OnboardingNavState.Loading,
            )

    fun completeOnboarding() {
        viewModelScope.launch {
            appPreferencesManager.setBoolean(key = prefKeyIsFinishedOnboard, value = true)
        }
    }
}
