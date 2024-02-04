package ch.app.hk.bank.locator.feature.onboarding.ui.onboard.screen

import androidx.compose.material3.Text
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import ch.app.hk.bank.locator.core.design.theme.AppTheme
import ch.app.hk.bank.locator.core.design.ui.ScreenState
import ch.app.hk.bank.locator.feature.onboarding.ui.onboard.state.OnboardUiState
import ch.app.hk.bank.locator.feature.onboarding.ui.onboard.viewmodel.OnboardViewModel
import io.mockk.Runs
import io.mockk.every
import io.mockk.just
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.flow.MutableStateFlow
import org.junit.Rule
import org.junit.Test

class OnboardScreenTest {
    @get:Rule
    val composeTestRule = createComposeRule()

    private val onboardViewModel = mockk<OnboardViewModel>()

    @Test
    fun testGoToHome() {
        val mockGoToHome = mockk<() -> Unit>()
        every { mockGoToHome() } just Runs

        every { onboardViewModel.uiState } returns
            MutableStateFlow(ScreenState.Success(OnboardUiState.GoToHome))

        composeTestRule.setContent {
            AppTheme {
                OnboardScreen(
                    onboardViewModel = onboardViewModel,
                    goToHome = mockGoToHome,
                    goToRequestPermission = {},
                )
            }
        }

        verify { mockGoToHome() }
    }

    @Test
    fun testOnboardingStartShown() {
        every { onboardViewModel.uiState } returns
            MutableStateFlow(ScreenState.Success(OnboardUiState.StartOnboarding))

        composeTestRule.setContent {
            AppTheme {
                OnboardScreen(
                    onboardViewModel = onboardViewModel,
                    goToHome = {},
                    goToRequestPermission = {},
                    startOnboarding = {
                        Text(
                            modifier = Modifier.testTag("test"),
                            text = "some text"
                        )
                    },
                )
            }
        }

        composeTestRule
            .onNodeWithTag("test")
            .assertIsDisplayed()
    }
}
