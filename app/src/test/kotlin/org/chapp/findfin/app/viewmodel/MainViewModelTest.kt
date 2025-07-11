package org.chapp.findfin.app.viewmodel

import androidx.appcompat.app.AppCompatDelegate
import app.cash.turbine.test
import io.kotest.matchers.shouldBe
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.chapp.findfin.feature.setting.data.repo.preference.model.Theme
import org.chapp.findfin.feature.setting.data.repo.preference.repository.UserSettingRepository
import org.chapp.findfin.testing.extension.MainDispatcherExtension
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

            // Act and Assert
            mainViewModel.themeModeFlow.test {
                awaitItem() shouldBe null
                awaitItem() shouldBe AppCompatDelegate.MODE_NIGHT_YES
                cancelAndIgnoreRemainingEvents()
            }
        }

    private fun createMainViewModel() = MainViewModel(userSettingRepository)
}
