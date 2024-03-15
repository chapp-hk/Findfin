package ch.app.hk.bank.locator.feature.onboarding.ui.permission.view

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import ch.app.hk.bank.locator.core.design.ui.AppContent
import ch.app.hk.bank.locator.core.design.ui.ScreenState
import ch.app.hk.bank.locator.feature.onboarding.ui.R
import ch.app.hk.bank.locator.feature.onboarding.ui.permission.viewmodel.PermissionViewModel
import ch.app.hk.bank.locator.testing.instrument.getResourceString
import io.mockk.Runs
import io.mockk.every
import io.mockk.just
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.flow.MutableStateFlow
import org.junit.Rule
import org.junit.Test

class RequestLocationPermissionScreenTest {
    @get:Rule
    val composeTestRule = createComposeRule()

    private val permissionViewModel = mockk<PermissionViewModel>()

    @Test
    fun assertInitialUiIsDisplayed() {
        every { permissionViewModel.uiState } returns
            MutableStateFlow(ScreenState.Empty)

        composeTestRule.setContent {
            AppContent {
                RequestLocationPermissionScreen(
                    permissionViewModel = permissionViewModel,
                    finishOnboarding = {},
                )
            }
        }

        composeTestRule
            .onNodeWithText(getResourceString(R.string.onboarding_title_enable_location))
            .assertIsDisplayed()
    }

    @Test
    fun verifySuccess() {
        every { permissionViewModel.uiState } returns
            MutableStateFlow(ScreenState.Success(true))

        val mockFinishOnboarding = mockk<() -> Unit>()
        every { mockFinishOnboarding() } just Runs

        composeTestRule.setContent {
            AppContent {
                RequestLocationPermissionScreen(
                    permissionViewModel = permissionViewModel,
                    finishOnboarding = mockFinishOnboarding,
                )
            }
        }

        verify { mockFinishOnboarding() }
    }
}
