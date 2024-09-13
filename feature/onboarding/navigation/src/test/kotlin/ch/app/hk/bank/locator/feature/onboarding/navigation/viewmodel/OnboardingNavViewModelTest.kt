package ch.app.hk.bank.locator.feature.onboarding.navigation.viewmodel

import app.cash.turbine.test
import ch.app.hk.bank.locator.core.preferences.AppPreferencesManager
import ch.app.hk.bank.locator.testing.extension.MainDispatcherExtension
import io.kotest.matchers.shouldBe
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

@ExtendWith(MainDispatcherExtension::class)
@DisplayName("OnboardingNavViewModel unit tests")
class OnboardingNavViewModelTest {
    private val appPreferencesManager = mockk<AppPreferencesManager>(relaxed = true)

    @Test
    fun `test navState IsFinishedOnboard`() {
        runTest {
            every { appPreferencesManager.getBoolean(any()) } returns flowOf(true)

            val onboardingNavViewModel = createOnboardingNavViewModel()

            verify {
                appPreferencesManager.getBoolean("onboarding_pref_key_is_finished_onboard")
            }

            onboardingNavViewModel.navState.test {
                awaitItem() shouldBe OnboardingNavState.Loading
                awaitItem() shouldBe OnboardingNavState.IsFinishedOnboard
                cancelAndIgnoreRemainingEvents()
            }
        }
    }

    @Test
    fun `test navState NotFinishedOnboard`() {
        runTest {
            every { appPreferencesManager.getBoolean(any()) } returns flowOf(false)

            val onboardingNavViewModel = createOnboardingNavViewModel()

            verify {
                appPreferencesManager.getBoolean("onboarding_pref_key_is_finished_onboard")
            }

            onboardingNavViewModel.navState.test {
                awaitItem() shouldBe OnboardingNavState.Loading
                awaitItem() shouldBe OnboardingNavState.NotFinishedOnboard
                cancelAndIgnoreRemainingEvents()
            }
        }
    }

    @Test
    fun `test completeOnboarding`() {
        runTest {
            every { appPreferencesManager.getBoolean(any()) } returns flowOf(false)

            val onboardingNavViewModel = createOnboardingNavViewModel()

            onboardingNavViewModel.completeOnboarding()

            // TODO - Find a better way to test this
            delay(500)
            coVerify {
                appPreferencesManager.setBoolean("onboarding_pref_key_is_finished_onboard", true)
            }
        }
    }

    private fun createOnboardingNavViewModel() = OnboardingNavViewModel(appPreferencesManager = appPreferencesManager)
}
