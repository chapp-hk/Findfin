package ch.app.hk.bank.locator.feature.setting.data.repo.preferece.local.datasource

import kotlinx.coroutines.flow.Flow

interface UserSettingLocalDataSource {
    suspend fun setLanguagePreference(language: String)

    fun getLanguagePreference(): Flow<String>

    suspend fun setThemePreference(theme: String)

    fun getThemePreference(): Flow<String>
}
