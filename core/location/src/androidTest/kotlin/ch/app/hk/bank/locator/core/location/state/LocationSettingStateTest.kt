package ch.app.hk.bank.locator.core.location.state

import androidx.activity.ComponentActivity
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithText
import ch.app.hk.bank.locator.core.location.state.helper.setting.SettingHelper
import io.mockk.every
import io.mockk.mockk
import org.junit.Rule
import org.junit.Test

class LocationSettingStateTest {
    @get:Rule
    val composeTestRule = createAndroidComposeRule<ComponentActivity>()

    @Test
    fun testRememberLocationSettingState() {
        composeTestRule.setContent {
            val locationSettingState = rememberLocationSettingState()

            ResultText(locationSettingState.result)
        }

        composeTestRule
            .onNodeWithText("Enabled")
            .assertIsDisplayed()
    }

    @Test
    fun testMutableLocationSettingStateDisabled() {
        val settingHelper = mockk<SettingHelper>()
        every { settingHelper.getSettings() } returns LocationSettingResult.Disabled

        composeTestRule.setContent {
            val locationSettingState = rememberMutableLocationSettingState(settingHelper) {}

            ResultText(locationSettingState.result)
        }

        composeTestRule
            .onNodeWithText("Disabled")
            .assertIsDisplayed()
    }

    @Test
    fun testMutableLocationSettingStateEnabled() {
        val settingHelper = mockk<SettingHelper>()
        every { settingHelper.getSettings() } returns LocationSettingResult.Enabled

        composeTestRule.setContent {
            val locationSettingState = rememberMutableLocationSettingState(settingHelper) {}

            ResultText(locationSettingState.result)
        }

        composeTestRule
            .onNodeWithText("Enabled")
            .assertIsDisplayed()
    }

    @Test
    fun testMutableLocationSettingStateNoSensor() {
        val settingHelper = mockk<SettingHelper>()
        every { settingHelper.getSettings() } returns LocationSettingResult.NoSensor

        composeTestRule.setContent {
            val locationSettingState = rememberMutableLocationSettingState(settingHelper) {}

            ResultText(locationSettingState.result)
        }

        composeTestRule
            .onNodeWithText("NoSensor")
            .assertIsDisplayed()
    }

    @Composable
    private fun ResultText(result: LocationSettingResult) {
        when (result) {
            LocationSettingResult.Disabled -> Text("Disabled")
            LocationSettingResult.Enabled -> Text("Enabled")
            LocationSettingResult.NoSensor -> Text("NoSensor")
            LocationSettingResult.Loading -> Text("None")
        }
    }
}
