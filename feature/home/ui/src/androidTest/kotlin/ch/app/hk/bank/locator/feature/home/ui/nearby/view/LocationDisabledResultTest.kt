package ch.app.hk.bank.locator.feature.home.ui.nearby.view

import androidx.activity.compose.LocalActivityResultRegistryOwner
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.test.core.app.ApplicationProvider
import ch.app.hk.bank.locator.feature.home.ui.R
import ch.app.hk.bank.locator.testing.instrument.ActivityResultTestRule
import ch.app.hk.bank.locator.testing.instrument.HiltComponentActivity
import ch.app.hk.bank.locator.testing.instrument.getResourceString
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import io.mockk.Called
import io.mockk.Runs
import io.mockk.every
import io.mockk.just
import io.mockk.mockk
import io.mockk.verify
import org.junit.Rule
import org.junit.Test

@HiltAndroidTest
class LocationDisabledResultTest {
    @get:Rule(order = 0)
    val hiltTestRule = HiltAndroidRule(this)

    @get:Rule(order = 1)
    val composeTestRule = createAndroidComposeRule<HiltComponentActivity>()

    @get:Rule(order = 2)
    val activityResultTestRule = ActivityResultTestRule(ApplicationProvider.getApplicationContext())

    @Test
    fun clickActionButton_ShouldInvoke_OnLocationServiceEnabled() {
        val registryOwner = activityResultTestRule.registryOwner(mockedActivityResult = true)

        val onLocationServiceEnabled = mockk<() -> Unit>()
        every { onLocationServiceEnabled() } just Runs

        composeTestRule.setContent {
            CompositionLocalProvider(LocalActivityResultRegistryOwner provides registryOwner) {
                LocationDisabledResult(onLocationServiceEnabled = onLocationServiceEnabled)
            }
        }

        composeTestRule
            .onNodeWithText(getResourceString(R.string.home_button_location_settings))
            .performClick()

        verify { onLocationServiceEnabled() }
    }

    @Test
    fun clickActionButton_ShouldNotInvoke_OnLocationServiceEnabled() {
        val registryOwner = activityResultTestRule.registryOwner(mockedActivityResult = false)
        val onLocationServiceEnabled = mockk<() -> Unit>()

        composeTestRule.setContent {
            CompositionLocalProvider(LocalActivityResultRegistryOwner provides registryOwner) {
                LocationDisabledResult(onLocationServiceEnabled = onLocationServiceEnabled)
            }
        }

        composeTestRule
            .onNodeWithText(getResourceString(R.string.home_button_location_settings))
            .performClick()

        verify { onLocationServiceEnabled wasNot Called }
    }
}
