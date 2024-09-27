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

class LocationStateTest {
    @get:Rule
    val composeTestRule = createAndroidComposeRule<ComponentActivity>()

    @Test
    fun testRememberLocationSettingState() {
        composeTestRule.setContent {
            val locationSettingState = rememberLocationState()

            ResultText(locationSettingState.result)
        }

        composeTestRule
            .onNodeWithText("Enabled")
            .assertIsDisplayed()
    }

    @Test
    fun testMutableLocationSettingStateDisabled() {
        val settingHelper = mockk<SettingHelper>()
        every { settingHelper.getSettings() } returns LocationStateResult.Disabled

        composeTestRule.setContent {
            val locationSettingState = rememberMutableLocationState(settingHelper) {}

            ResultText(locationSettingState.result)
        }

        composeTestRule
            .onNodeWithText("Disabled")
            .assertIsDisplayed()
    }

    @Test
    fun testMutableLocationSettingStateEnabled() {
        val settingHelper = mockk<SettingHelper>()
        every { settingHelper.getSettings() } returns LocationStateResult.Enabled

        composeTestRule.setContent {
            val locationSettingState = rememberMutableLocationState(settingHelper) {}

            ResultText(locationSettingState.result)
        }

        composeTestRule
            .onNodeWithText("Enabled")
            .assertIsDisplayed()
    }

    @Test
    fun testMutableLocationSettingStateNoSensor() {
        val settingHelper = mockk<SettingHelper>()
        every { settingHelper.getSettings() } returns LocationStateResult.NoSensor

        composeTestRule.setContent {
            val locationSettingState = rememberMutableLocationState(settingHelper) {}

            ResultText(locationSettingState.result)
        }

        composeTestRule
            .onNodeWithText("NoSensor")
            .assertIsDisplayed()
    }

    @Composable
    private fun ResultText(result: LocationStateResult) {
        when (result) {
            LocationStateResult.Disabled -> Text("Disabled")
            LocationStateResult.Enabled -> Text("Enabled")
            LocationStateResult.NoSensor -> Text("NoSensor")
            LocationStateResult.Loading -> Text("None")
        }
    }
}
