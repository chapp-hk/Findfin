package org.chapp.findfin.feature.setting.data.repo.preference.local.datasource

import kotlinx.coroutines.flow.Flow

interface UserSettingLocalDataSource {
    suspend fun setLanguagePreference(language: String)

    fun getLanguagePreference(): Flow<String>

    suspend fun setThemePreference(theme: String)

    fun getThemePreference(): Flow<String>
}
