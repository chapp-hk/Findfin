package ch.app.hk.bank.locator.feature.onboarding.ui.onboard.view

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithTag
import ch.app.hk.bank.locator.core.design.theme.AppTheme
import ch.app.hk.bank.locator.core.design.ui.ScreenState
import ch.app.hk.bank.locator.feature.onboarding.ui.onboard.state.OnboardUiState
import ch.app.hk.bank.locator.feature.onboarding.ui.onboard.viewmodel.OnboardViewModel
import ch.app.hk.bank.locator.testing.instrument.HiltComponentActivity
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import io.mockk.Runs
import io.mockk.every
import io.mockk.just
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.flow.MutableStateFlow
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@HiltAndroidTest
class OnboardScreenTest {
    @get:Rule(order = 0)
    val hiltTestRule = HiltAndroidRule(this)

    @get:Rule(order = 1)
    val composeTestRule = createAndroidComposeRule<HiltComponentActivity>()

    private val onboardViewModel = mockk<OnboardViewModel>()

    @Before
    fun setup() {
        hiltTestRule.inject()
    }

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
                )
            }
        }

        composeTestRule
            .onNodeWithTag(TEST_TAG_ONBOARDING_SELECT_LANGUAGE_SCREEN)
            .assertIsDisplayed()
    }
}
