package ch.app.hk.bank.locator.feature.setting.data.repo.preferece.repository

import ch.app.hk.bank.locator.core.threading.DispatcherIo
import ch.app.hk.bank.locator.feature.setting.data.repo.preferece.local.datasource.UserSettingLocalDataSource
import ch.app.hk.bank.locator.feature.setting.data.repo.preferece.model.Theme
import ch.app.library.hiltwrap.annotation.HiltWrapBindModule
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
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
            userSettingLocalDataSource.setThemePreference(
                when (theme) {
                    Theme.Light -> "LIGHT"
                    Theme.Dark -> "DARK"
                    Theme.System -> "SYSTEM"
                },
            )
        }
    }

    override fun getThemePreference(): Flow<Theme> {
        return userSettingLocalDataSource
            .getThemePreference()
            .map { value ->
                when (value) {
                    "LIGHT" -> Theme.Light
                    "DARK" -> Theme.Dark
                    else -> Theme.System
                }
            }
            .flowOn(ioDispatcher)
    }
}
