package ch.app.hk.bank.locator.feature.onboarding.ui.onboard.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ch.app.hk.bank.locator.core.preferences.api.AppPreferencesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class OnboardViewModelImpl @Inject constructor(
    appPreferencesRepository: AppPreferencesRepository,
) : ViewModel(), OnboardViewModel {

    override val uiState: SharedFlow<OnboardUiState> =
        appPreferencesRepository
            .getLocale()
            .map { locale ->
                if (locale == null) {
                    OnboardUiState.SelectLanguage
                } else {
                    OnboardUiState.NavigateToHome
                }
            }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5000L),
                initialValue = OnboardUiState.None,
            )
}
