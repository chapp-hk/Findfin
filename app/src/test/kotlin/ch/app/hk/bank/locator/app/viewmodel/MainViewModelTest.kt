package ch.app.hk.bank.locator.app.viewmodel

import app.cash.turbine.test
import ch.app.hk.bank.locator.feature.setting.data.repo.preferece.model.Theme
import ch.app.hk.bank.locator.feature.setting.data.repo.preferece.repository.UserSettingRepository
import ch.app.hk.bank.locator.testing.extension.MainDispatcherExtension
import io.kotest.matchers.shouldBe
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

@ExtendWith(MainDispatcherExtension::class)
class MainViewModelTest {
    private val userSettingRepository = mockk<UserSettingRepository>()

    @Test
    fun `test themeFlow emits correct theme`() =
        runTest {
            // Arrange
            val theme = Theme.DARK
            every { userSettingRepository.getThemePreference() } returns flowOf(theme)
            val mainViewModel = createMainViewModel()

            // Act
            val result = mainViewModel.themeFlow

            // Assert
            result.test {
                awaitItem() shouldBe null
                awaitItem() shouldBe theme
                cancelAndIgnoreRemainingEvents()
            }
        }

    private fun createMainViewModel() = MainViewModel(userSettingRepository)
}
