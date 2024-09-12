package ch.app.hk.bank.locator.feature.onboarding.ui.permission.view

import android.content.Context
import androidx.activity.compose.LocalActivityResultRegistryOwner
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.test.core.app.ApplicationProvider
import ch.app.hk.bank.locator.core.design.ui.AppContent
import ch.app.hk.bank.locator.feature.onboarding.ui.R
import ch.app.hk.bank.locator.testing.instrument.ActivityResultTestRule
import ch.app.hk.bank.locator.testing.instrument.getResourceString
import io.mockk.Runs
import io.mockk.every
import io.mockk.just
import io.mockk.mockk
import io.mockk.verify
import org.junit.Rule
import org.junit.Test

class RequestLocationPermissionScreenTest {
    private val context = ApplicationProvider.getApplicationContext<Context>()

    @get:Rule
    val composeTestRule = createComposeRule()

    @get:Rule
    val activityResultTestRule = ActivityResultTestRule(context = context)

    @Test
    fun testGrantedPermission() {
        val registryOwner = activityResultTestRule.registryOwner(mockedActivityResult = true)

        val mockFinishOnboarding = mockk<() -> Unit>()
        every { mockFinishOnboarding() } just Runs

        composeTestRule.setContent {
            CompositionLocalProvider(LocalActivityResultRegistryOwner provides registryOwner) {
                AppContent {
                    RequestLocationPermissionScreen(
                        onFinishRequestPermission = mockFinishOnboarding,
                    )
                }
            }
        }

        composeTestRule
            .onNodeWithText(getResourceString(R.string.onboarding_button_grant_permission))
            .performClick()

        verify { mockFinishOnboarding() }
    }

    @Test
    fun testDenyPermission() {
        val registryOwner = activityResultTestRule.registryOwner(mockedActivityResult = false)

        val mockFinishOnboarding = mockk<() -> Unit>(relaxed = true)

        composeTestRule.setContent {
            CompositionLocalProvider(LocalActivityResultRegistryOwner provides registryOwner) {
                AppContent {
                    RequestLocationPermissionScreen(
                        onFinishRequestPermission = mockFinishOnboarding,
                    )
                }
            }
        }

        composeTestRule
            .onNodeWithText(getResourceString(R.string.onboarding_button_grant_permission))
            .performClick()

        verify {
            mockFinishOnboarding()
        }
    }

    @Test
    fun testSkip() {
        val mockFinishOnboarding = mockk<() -> Unit>(relaxed = true)

        composeTestRule.setContent {
            AppContent {
                RequestLocationPermissionScreen(
                    onFinishRequestPermission = mockFinishOnboarding,
                )
            }
        }

        composeTestRule
            .onNodeWithText(getResourceString(R.string.onboarding_button_skip))
            .performClick()

        verify {
            mockFinishOnboarding()
        }
    }
}
