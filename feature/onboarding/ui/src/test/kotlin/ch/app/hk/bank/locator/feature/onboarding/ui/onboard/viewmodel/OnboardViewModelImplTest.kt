package ch.app.hk.bank.locator.feature.onboarding.ui.onboard.viewmodel

import app.cash.turbine.test
import ch.app.hk.bank.locator.core.preferences.api.AppPreferencesRepository
import ch.app.hk.bank.locator.testing.extension.MainDispatcherExtension
import io.kotest.matchers.shouldBe
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

@ExtendWith(MainDispatcherExtension::class)
@DisplayName("OnboardViewModelImpl unit tests")
class OnboardViewModelImplTest {
    private val appPreferencesRepository = mockk<AppPreferencesRepository>()

    @Test
    fun `test uiState with locale is null`() =
        runTest {
            every { appPreferencesRepository.getLocale() } returns flowOf(null)

            val onboardViewModel = createOnboardViewModel()

            onboardViewModel.uiState.test {
                awaitItem() shouldBe OnboardUiState.None
                awaitItem() shouldBe OnboardUiState.SelectLanguage
                cancelAndIgnoreRemainingEvents()
            }
        }

    @Test
    fun `test uiState with locale is not null`() =
        runTest {
            every { appPreferencesRepository.getLocale() } returns flowOf("zh-HK")

            val onboardViewModel = createOnboardViewModel()

            onboardViewModel.uiState.test {
                awaitItem() shouldBe OnboardUiState.None
                awaitItem() shouldBe OnboardUiState.NavigateToHome
                cancelAndIgnoreRemainingEvents()
            }
        }

    private fun createOnboardViewModel() =
        OnboardViewModelImpl(appPreferencesRepository = appPreferencesRepository)
}
