package ch.app.hk.bank.locator.core.location.state

import androidx.activity.ComponentActivity
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithText
import ch.app.hk.bank.locator.core.location.setting.LocationSettingStatus
import ch.app.hk.bank.locator.core.location.setting.SettingHelper
import ch.app.hk.bank.locator.core.location.setting.rememberLocationSettingState
import ch.app.hk.bank.locator.core.location.setting.rememberMutableLocationState
import io.mockk.every
import io.mockk.mockk
import org.junit.Rule
import org.junit.Test

class LocationStateTest {
    @get:Rule
    val composeTestRule = createAndroidComposeRule<ComponentActivity>()

    @Test
    fun testRememberLocationSettingState() {
        composeTestRule.setContent {
            val locationSettingState = rememberLocationSettingState()

            ResultText(locationSettingState.status)
        }

        composeTestRule
            .onNodeWithText("Enabled")
            .assertIsDisplayed()
    }

    @Test
    fun testMutableLocationSettingStateDisabled() {
        val settingHelper = mockk<SettingHelper>()
        every { settingHelper.getSettings() } returns LocationSettingStatus.Disabled

        composeTestRule.setContent {
            val locationSettingState = rememberMutableLocationState(settingHelper) {}

            ResultText(locationSettingState.status)
        }

        composeTestRule
            .onNodeWithText("Disabled")
            .assertIsDisplayed()
    }

    @Test
    fun testMutableLocationSettingStateEnabled() {
        val settingHelper = mockk<SettingHelper>()
        every { settingHelper.getSettings() } returns LocationSettingStatus.Enabled

        composeTestRule.setContent {
            val locationSettingState = rememberMutableLocationState(settingHelper) {}

            ResultText(locationSettingState.status)
        }

        composeTestRule
            .onNodeWithText("Enabled")
            .assertIsDisplayed()
    }

    @Test
    fun testMutableLocationSettingStateNoSensor() {
        val settingHelper = mockk<SettingHelper>()
        every { settingHelper.getSettings() } returns LocationSettingStatus.NoSensor

        composeTestRule.setContent {
            val locationSettingState = rememberMutableLocationState(settingHelper) {}

            ResultText(locationSettingState.status)
        }

        composeTestRule
            .onNodeWithText("NoSensor")
            .assertIsDisplayed()
    }

    @Composable
    private fun ResultText(result: LocationSettingStatus) {
        when (result) {
            LocationSettingStatus.Disabled -> Text("Disabled")
            LocationSettingStatus.Enabled -> Text("Enabled")
            LocationSettingStatus.NoSensor -> Text("NoSensor")
        }
    }
}
