package ch.app.hk.bank.locator.feature.onboarding.ui.permission.view

import androidx.compose.material3.Scaffold
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import ch.app.hk.bank.locator.core.design.ui.AppContent
import io.mockk.Runs
import io.mockk.every
import io.mockk.just
import io.mockk.mockk
import io.mockk.verify
import org.junit.Rule
import org.junit.Test

class LocationPermissionNotGrantedDialogTest {
    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun assertDialogIsDisplayed() {
        composeTestRule.setContent {
            AppContent {
                Scaffold {
                    it.calculateBottomPadding()
                    LocationPermissionNotGrantedDialog(true) {
                    }
                }
            }
        }

        composeTestRule
            .onNodeWithTag(TEST_TAG_ONBOARDING_LOCATION_PERMISSION_NOT_GRANTED_DIALOG)
            .assertIsDisplayed()
    }

    @Test
    fun verifyConfirmButton() {
        val mockedOnConfirm = mockk<() -> Unit>()
        every { mockedOnConfirm() } just Runs

        composeTestRule.setContent {
            AppContent {
                Scaffold {
                    it.calculateBottomPadding()
                    LocationPermissionNotGrantedDialog(
                        isShowDialog = true,
                        onConfirm = mockedOnConfirm,
                    )
                }
            }
        }

        composeTestRule
            .onNodeWithTag(TEST_TAG_ONBOARDING_LOCATION_PERMISSION_NOT_GRANTED_DIALOG_BUTTON)
            .performClick()

        verify { mockedOnConfirm() }
    }
}
