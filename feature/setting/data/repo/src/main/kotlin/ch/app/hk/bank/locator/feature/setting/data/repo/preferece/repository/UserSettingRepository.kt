package ch.app.hk.bank.locator.feature.setting.data.repo.preferece.repository

import ch.app.hk.bank.locator.feature.setting.data.repo.preferece.model.Theme
import kotlinx.coroutines.flow.Flow

interface UserSettingRepository {
    suspend fun setLanguagePreference(language: String)

    fun getLanguagePreference(): Flow<String>

    suspend fun setThemePreference(theme: Theme)

    fun getThemePreference(): Flow<Theme>
}
