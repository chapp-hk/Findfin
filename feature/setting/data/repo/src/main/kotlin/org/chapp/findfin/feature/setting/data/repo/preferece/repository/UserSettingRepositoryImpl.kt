package org.chapp.findfin.feature.setting.data.repo.preferece.repository

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import org.chapp.findfin.core.threading.DispatcherIo
import org.chapp.findfin.feature.setting.data.repo.preferece.local.datasource.UserSettingLocalDataSource
import org.chapp.findfin.feature.setting.data.repo.preferece.model.Theme
import org.chapp.library.hiltwrap.annotation.HiltWrapBindModule
import javax.inject.Inject

@HiltWrapBindModule
internal class UserSettingRepositoryImpl @Inject constructor(
    @DispatcherIo private val ioDispatcher: CoroutineDispatcher,
    private val userSettingLocalDataSource: UserSettingLocalDataSource,
) : UserSettingRepository {
    override suspend fun setLanguagePreference(language: String) {
        withContext(ioDispatcher) {
            userSettingLocalDataSource.setLanguagePreference(language)
        }
    }

    override fun getLanguagePreference(): Flow<String> {
        return userSettingLocalDataSource
            .getLanguagePreference()
            .flowOn(ioDispatcher)
    }

    override suspend fun setThemePreference(theme: Theme) {
        withContext(ioDispatcher) {
            userSettingLocalDataSource.setThemePreference(theme.themeValue)
        }
    }

    override fun getThemePreference(): Flow<Theme> {
        return userSettingLocalDataSource
            .getThemePreference()
            .map { Theme.fromValue(it) }
            .flowOn(ioDispatcher)
    }
}
