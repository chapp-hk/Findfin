package org.chapp.findfin.feature.setting.data.local.preferences

import io.kotest.matchers.shouldBe
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.chapp.findfin.core.preferences.storage.AppPreferencesManager
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test

@DisplayName("PreferencesDataSource unit tests")
class PreferencesDataSourceTest {
    private val appPreferencesManager = mockk<AppPreferencesManager>(relaxUnitFun = true)

    private val preferencesDataSource = PreferencesDataSource(appPreferencesManager)

    @Test
    fun `setLanguagePreference should call setString on AppPreferencesManager`() {
        runTest {
            val language = "en"

            preferencesDataSource.setLanguagePreference(language)

            coVerify { appPreferencesManager.setString("setting_pref_key_language", language) }
        }
    }

    @Test
    fun `getLanguagePreference should return the correct Flow from AppPreferencesManager`() {
        runTest {
            val language = "en"
            coEvery { appPreferencesManager.getString("setting_pref_key_language") } returns
                flowOf(language)

            val result = preferencesDataSource.getLanguagePreference()

            result.first() shouldBe language
        }
    }

    @Test
    fun `setThemePreference should call setString on AppPreferencesManager`() {
        runTest {
            val theme = "DARK"

            preferencesDataSource.setThemePreference(theme)

            coVerify { appPreferencesManager.setString("setting_pref_key_theme", theme) }
        }
    }

    @Test
    fun `getThemePreference should return the correct Flow from AppPreferencesManager`() {
        runTest {
            val theme = "DARK"
            coEvery { appPreferencesManager.getString("setting_pref_key_theme") } returns
                flowOf(theme)

            val result = preferencesDataSource.getThemePreference()

            result.first() shouldBe theme
        }
    }
}
