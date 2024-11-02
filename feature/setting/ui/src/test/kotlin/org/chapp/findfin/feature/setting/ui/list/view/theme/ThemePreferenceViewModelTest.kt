package org.chapp.findfin.feature.setting.ui.list.view.theme

import app.cash.turbine.test
import io.kotest.matchers.shouldBe
import io.mockk.Runs
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.just
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.chapp.findfin.feature.setting.data.repo.preferece.model.Theme
import org.chapp.findfin.feature.setting.data.repo.preferece.repository.UserSettingRepository
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test

@DisplayName("ThemePreferenceViewModel unit tests")
class ThemePreferenceViewModelTest {
    private val userSettingRepository = mockk<UserSettingRepository>()
    private val viewModel = ThemePreferenceViewModel(userSettingRepository)

    @Test
    fun `getCurrentTheme should return current theme`() =
        runTest {
            // Arrange
            val themeValue = "DARK"
            every { userSettingRepository.getThemePreference() } returns
                flowOf(Theme.fromValue(themeValue))

            viewModel.getCurrentTheme().test {
                awaitItem() shouldBe themeValue
                cancelAndIgnoreRemainingEvents()
            }

            verify { userSettingRepository.getThemePreference() }
        }

    @Test
    fun `setTheme should set the theme preference`() =
        runTest {
            // Arrange
            val themeValue = "LIGHT"
            coEvery { userSettingRepository.setThemePreference(any()) } just Runs

            // Act
            viewModel.setTheme(themeValue)

            // Assert
            coVerify { userSettingRepository.setThemePreference(Theme.fromValue(themeValue)) }
        }
}
