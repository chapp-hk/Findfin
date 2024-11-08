package org.chapp.findfin.feature.setting.presentation.ui.list.view.theme

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import org.chapp.findfin.feature.setting.data.repo.preferece.model.Theme
import org.chapp.findfin.feature.setting.data.repo.preferece.repository.UserSettingRepository
import javax.inject.Inject

@HiltViewModel
internal class ThemePreferenceViewModel @Inject constructor(
    private val userSettingRepository: UserSettingRepository,
) : ViewModel() {
    fun getCurrentTheme(): Flow<String> {
        return userSettingRepository.getThemePreference().map { it.themeValue }
    }

    suspend fun setTheme(value: String) {
        userSettingRepository.setThemePreference(Theme.fromValue(value))
    }
}
