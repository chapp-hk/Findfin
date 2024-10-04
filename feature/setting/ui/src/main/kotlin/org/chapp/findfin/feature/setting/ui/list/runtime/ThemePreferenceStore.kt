package org.chapp.findfin.feature.setting.ui.list.runtime

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import dagger.hilt.EntryPoint
import dagger.hilt.InstallIn
import dagger.hilt.android.EntryPointAccessors
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import org.chapp.findfin.core.preferences.runtime.PreferenceStore
import org.chapp.findfin.feature.setting.data.repo.preferece.model.Theme
import org.chapp.findfin.feature.setting.data.repo.preferece.repository.UserSettingRepository
import javax.inject.Inject

@Composable
internal fun rememberThemePreferenceStore(): PreferenceStore<String> {
    val context = LocalContext.current

    return remember {
        EntryPointAccessors.fromApplication(
            context,
            ThemePreferenceStoreEntryPoint::class.java,
        ).themePreferenceStore()
    }
}

internal class ThemePreferenceStore @Inject constructor(
    private val userSettingRepository: UserSettingRepository,
) : PreferenceStore<String> {
    override fun get(): Flow<String> {
        return userSettingRepository.getThemePreference().map { it.themeValue }
    }

    override suspend fun set(value: String) {
        userSettingRepository.setThemePreference(Theme.fromValue(value))
    }
}

@EntryPoint
@InstallIn(SingletonComponent::class)
private interface ThemePreferenceStoreEntryPoint {
    fun themePreferenceStore(): ThemePreferenceStore
}
