package org.chapp.findfin.feature.setting.data.repo.preference.repository

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import org.chapp.findfin.feature.setting.data.repo.preference.local.datasource.UserSettingLocalDataSource
import org.chapp.findfin.feature.setting.data.repo.preference.model.Theme
import org.chapp.library.hiltwrap.annotation.HiltWrapBindModule
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
@HiltWrapBindModule
internal class UserSettingRepositoryImpl @Inject constructor(
    private val userSettingLocalDataSource: UserSettingLocalDataSource,
) : UserSettingRepository {
    override suspend fun setLanguagePreference(language: String) {
        userSettingLocalDataSource.setLanguagePreference(language)
    }

    override fun getLanguagePreference(): Flow<String> {
        return userSettingLocalDataSource.getLanguagePreference()
    }

    override suspend fun setThemePreference(theme: Theme) {
        userSettingLocalDataSource.setThemePreference(theme.themeValue)
    }

    override fun getThemePreference(): Flow<Theme> {
        return userSettingLocalDataSource
            .getThemePreference()
            .map { Theme.fromValue(it) }
    }
}
