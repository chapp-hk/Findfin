package ch.app.hk.bank.locator.feature.setting.data.local.preferences

import ch.app.hk.bank.locator.core.preferences.AppPreferencesManager
import ch.app.hk.bank.locator.feature.setting.data.repo.preferece.local.datasource.UserSettingLocalDataSource
import ch.app.library.hiltwrap.annotation.HiltWrapBindModule
import kotlinx.coroutines.flow.Flow
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
