package org.chapp.findfin.feature.auth.navigation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import org.chapp.findfin.core.preferences.storage.AppPreferencesManager
import javax.inject.Inject

@HiltViewModel
internal class AuthNavViewModel @Inject constructor(
    private val appPreferencesManager: AppPreferencesManager,
) : ViewModel() {
    val navState: StateFlow<AuthNavState> =
        appPreferencesManager
            .getBoolean(key = PREF_KEY_IS_INITIALIZED_AUTH)
            .map { value ->
                if (value) {
                    AuthNavState.IsInitialized
                } else {
                    AuthNavState.NotInitialized
                }
            }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(),
                initialValue = AuthNavState.Loading,
            )

    fun setAuthInitialized() {
        viewModelScope.launch {
            appPreferencesManager.setBoolean(key = PREF_KEY_IS_INITIALIZED_AUTH, value = true)
        }
    }

    private companion object {
        const val PREF_KEY_IS_INITIALIZED_AUTH = "auth_pref_key_is_initialized_auth"
    }
}
