package ch.app.hk.bank.locator.feature.onboarding.ui.permission.view

import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import ch.app.hk.bank.locator.core.design.theme.AppTheme
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
            AppTheme {
                RequestLocationPermissionContent(
                    onGrantPermission = mockOnGrantPermission,
                    onSkip = {},
                )
            }
        }

        composeTestRule
            .onNodeWithTag(TEST_TAG_ONBOARDING_REQUEST_LOCATION_PERMISSION_BUTTON_GRANT)
            .performClick()

        verify { mockOnGrantPermission() }
    }

    @Test
    fun verifySkip() {
        val mockOnSkip = mockk<() -> Unit>()
        every { mockOnSkip() } just Runs

        composeTestRule.setContent {
            AppTheme {
                RequestLocationPermissionContent(
                    onGrantPermission = {},
                    onSkip = mockOnSkip,
                )
            }
        }

        composeTestRule
            .onNodeWithTag(TEST_TAG_ONBOARDING_REQUEST_LOCATION_PERMISSION_BUTTON_SKIP)
            .performClick()

        verify { mockOnSkip() }
    }
}
