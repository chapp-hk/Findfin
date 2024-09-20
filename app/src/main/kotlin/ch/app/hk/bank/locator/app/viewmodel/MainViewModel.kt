package ch.app.hk.bank.locator.app.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ch.app.hk.bank.locator.feature.setting.data.repo.preferece.model.Theme
import ch.app.hk.bank.locator.feature.setting.data.repo.preferece.repository.UserSettingRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
internal class MainViewModel @Inject constructor(
    userSettingRepository: UserSettingRepository,
) : ViewModel() {
    val themeFlow: StateFlow<Theme?> =
        userSettingRepository
            .getThemePreference()
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(),
                initialValue = null,
            )
}
