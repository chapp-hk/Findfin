package ch.app.hk.bank.locator.feature.setting.ui.list.runtime

import app.cash.turbine.test
import ch.app.hk.bank.locator.feature.setting.data.repo.preferece.model.Theme
import ch.app.hk.bank.locator.feature.setting.data.repo.preferece.repository.UserSettingRepository
import io.kotest.matchers.shouldBe
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test

@DisplayName("ThemePreferenceStore unit tests")
class ThemePreferenceStoreTest {
    private val userSettingRepository = mockk<UserSettingRepository>(relaxUnitFun = true)

    private val themePreferenceStore =
        ThemePreferenceStore(userSettingRepository = userSettingRepository)

    @Test
    fun `test get theme preference`() =
        runTest {
            // Arrange
            val theme = Theme.DARK
            every { userSettingRepository.getThemePreference() } returns flowOf(theme)

            // Act
            val result = themePreferenceStore.get()

            // Assert
            result.test {
                awaitItem() shouldBe theme.themeValue
                cancelAndIgnoreRemainingEvents()
            }
        }

    @Test
    fun `test set theme preference`() =
        runTest {
            // Arrange
            val themeValue = "DARK"
            val theme = Theme.fromValue(themeValue)

            // Act
            themePreferenceStore.set(themeValue)

            // Assert
            coVerify { userSettingRepository.setThemePreference(theme) }
        }
}
