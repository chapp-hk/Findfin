package org.chapp.findfin.feature.setting.data.local.preferences

import kotlinx.coroutines.flow.Flow
import org.chapp.findfin.core.preferences.storage.AppPreferencesManager
import org.chapp.findfin.feature.setting.data.repo.preferece.local.datasource.UserSettingLocalDataSource
import org.chapp.library.hiltwrap.annotation.HiltWrapBindModule
import javax.inject.Inject

@HiltWrapBindModule
internal class PreferencesDataSource @Inject constructor(
    private val appPreferencesManager: AppPreferencesManager,
) : UserSettingLocalDataSource {
    override suspend fun setLanguagePreference(language: String) {
        appPreferencesManager.setString(SETTING_PREF_KEY_LANGUAGE, language)
    }

    override fun getLanguagePreference(): Flow<String> {
        return appPreferencesManager.getString(SETTING_PREF_KEY_LANGUAGE)
    }

    override suspend fun setThemePreference(theme: String) {
        appPreferencesManager.setString(SETTING_PREF_KEY_THEME, theme)
    }

    override fun getThemePreference(): Flow<String> {
        return appPreferencesManager.getString(SETTING_PREF_KEY_THEME)
    }

    private companion object {
        const val SETTING_PREF_KEY_LANGUAGE = "setting_pref_key_language"
        const val SETTING_PREF_KEY_THEME = "setting_pref_key_theme"
    }
}
