package ch.app.hk.bank.locator.app.viewmodel

import androidx.appcompat.app.AppCompatDelegate
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ch.app.hk.bank.locator.feature.setting.data.repo.preferece.model.Theme
import ch.app.hk.bank.locator.feature.setting.data.repo.preferece.repository.UserSettingRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
internal class MainViewModel @Inject constructor(
    userSettingRepository: UserSettingRepository,
) : ViewModel() {
    val themeModeFlow: StateFlow<Int?> =
        userSettingRepository
            .getThemePreference()
            .map { theme ->
                when (theme) {
                    Theme.LIGHT -> AppCompatDelegate.MODE_NIGHT_NO
                    Theme.DARK -> AppCompatDelegate.MODE_NIGHT_YES
                    Theme.SYSTEM -> AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM
                }
            }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(),
                initialValue = null,
            )
}
