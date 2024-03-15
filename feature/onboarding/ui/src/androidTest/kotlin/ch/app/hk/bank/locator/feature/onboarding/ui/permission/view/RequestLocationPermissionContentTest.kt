package ch.app.hk.bank.locator.feature.onboarding.ui.permission.view

import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import ch.app.hk.bank.locator.core.design.ui.AppContent
import ch.app.hk.bank.locator.feature.onboarding.ui.R
import ch.app.hk.bank.locator.testing.instrument.getResourceString
import io.mockk.Runs
import io.mockk.every
import io.mockk.just
import io.mockk.mockk
import io.mockk.verify
import org.junit.Rule
import org.junit.Test

class RequestLocationPermissionContentTest {
    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun verifyOnGrantPermission() {
        val mockOnGrantPermission = mockk<() -> Unit>()
        every { mockOnGrantPermission() } just Runs

        composeTestRule.setContent {
            AppContent {
                RequestLocationPermissionContent(
                    onGrantPermission = mockOnGrantPermission,
                    onSkip = {},
                )
            }
        }

        composeTestRule
            .onNodeWithText(getResourceString(R.string.onboarding_button_grant_permission))
            .performClick()

        verify { mockOnGrantPermission() }
    }

    @Test
    fun verifySkip() {
        val mockOnSkip = mockk<() -> Unit>()
        every { mockOnSkip() } just Runs

        composeTestRule.setContent {
            AppContent {
                RequestLocationPermissionContent(
                    onGrantPermission = {},
                    onSkip = mockOnSkip,
                )
            }
        }

        composeTestRule
            .onNodeWithText(getResourceString(R.string.onboarding_button_skip))
            .performClick()

        verify { mockOnSkip() }
    }
}
