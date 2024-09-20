package ch.app.hk.bank.locator.feature.setting.data.repo.preferece.repository

import ch.app.hk.bank.locator.feature.setting.data.repo.preferece.local.datasource.UserSettingLocalDataSource
import ch.app.hk.bank.locator.feature.setting.data.repo.preferece.model.Theme
import io.kotest.matchers.shouldBe
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Test

class UserSettingRepositoryImplTest {
    private val testDispatcher = StandardTestDispatcher()
    private val userSettingLocalDataSource = mockk<UserSettingLocalDataSource>(relaxUnitFun = true)

    private val userSettingRepository =
        UserSettingRepositoryImpl(
            testDispatcher,
            userSettingLocalDataSource,
        )

    @Test
    fun `setLanguagePreference should call setLanguagePreference on UserSettingLocalDataSource`() {
        runTest(testDispatcher) {
            val language = "en"

            userSettingRepository.setLanguagePreference(language)

            coVerify { userSettingLocalDataSource.setLanguagePreference(language) }
        }
    }

    @Test
    fun `getLanguagePreference should return the correct Flow from UserSettingLocalDataSource`() {
        runTest(testDispatcher) {
            val language = "en"
            coEvery { userSettingLocalDataSource.getLanguagePreference() } returns flowOf(language)

            val result = userSettingRepository.getLanguagePreference()

            result.first() shouldBe language
        }
    }

    @Test
    fun `setThemePreference should call setThemePreference on UserSettingLocalDataSource`() {
        runTest(testDispatcher) {
            val theme = Theme.DARK

            userSettingRepository.setThemePreference(theme)

            coVerify { userSettingLocalDataSource.setThemePreference("DARK") }
        }
    }

    @Test
    fun `getThemePreference should return the correct Flow from UserSettingLocalDataSource`() {
        runTest(testDispatcher) {
            val theme = "DARK"
            coEvery { userSettingLocalDataSource.getThemePreference() } returns flowOf(theme)

            val result = userSettingRepository.getThemePreference()

            result.first() shouldBe Theme.DARK
        }
    }
}
