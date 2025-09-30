package org.chapp.findfin.feature.setting.data.repo.preference.repository

import io.kotest.matchers.shouldBe
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.chapp.findfin.feature.setting.data.repo.preference.local.datasource.UserSettingLocalDataSource
import org.chapp.findfin.feature.setting.data.repo.preference.model.Theme
import org.junit.jupiter.api.Test

class UserSettingRepositoryImplTest {
    private val userSettingLocalDataSource = mockk<UserSettingLocalDataSource>(relaxUnitFun = true)

    private val userSettingRepository =
        UserSettingRepositoryImpl(
            userSettingLocalDataSource = userSettingLocalDataSource,
        )

    @Test
    fun `setLanguagePreference should call setLanguagePreference on UserSettingLocalDataSource`() {
        runTest {
            val language = "en"

            userSettingRepository.setLanguagePreference(language)

            coVerify { userSettingLocalDataSource.setLanguagePreference(language) }
        }
    }

    @Test
    fun `getLanguagePreference should return the correct Flow from UserSettingLocalDataSource`() {
        runTest {
            val language = "en"
            coEvery { userSettingLocalDataSource.getLanguagePreference() } returns flowOf(language)

            val result = userSettingRepository.getLanguagePreference()

            result.first() shouldBe language
        }
    }

    @Test
    fun `setThemePreference should call setThemePreference on UserSettingLocalDataSource`() {
        runTest {
            val theme = Theme.DARK

            userSettingRepository.setThemePreference(theme)

            coVerify { userSettingLocalDataSource.setThemePreference("DARK") }
        }
    }

    @Test
    fun `getThemePreference should return the correct Flow from UserSettingLocalDataSource`() {
        runTest {
            val theme = "DARK"
            coEvery { userSettingLocalDataSource.getThemePreference() } returns flowOf(theme)

            val result = userSettingRepository.getThemePreference()

            result.first() shouldBe Theme.DARK
        }
    }
}
