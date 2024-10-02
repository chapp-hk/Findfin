package ch.app.hk.bank.locator.core.location.setting

import androidx.activity.ComponentActivity
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import io.kotest.matchers.shouldBe
import io.mockk.every
import io.mockk.mockk
import org.junit.Rule
import org.junit.Test

class MutableLocationSettingStateTest {
    @get:Rule
    val composeTestRule = createAndroidComposeRule<ComponentActivity>()

    @Test
    fun testMutableLocationSettingStateDisabled() {
        val settingHelper = mockk<SettingHelper>()
        every { settingHelper.getSettings() } returns LocationSettingStatus.Disabled

        composeTestRule.setContent {
            val locationSettingState = rememberMutableLocationState(settingHelper) {}

            locationSettingState.status shouldBe LocationSettingStatus.Disabled
        }
    }

    @Test
    fun testMutableLocationSettingStateEnabled() {
        val settingHelper = mockk<SettingHelper>()
        every { settingHelper.getSettings() } returns LocationSettingStatus.Enabled

        composeTestRule.setContent {
            val locationSettingState = rememberMutableLocationState(settingHelper) {}

            locationSettingState.status shouldBe LocationSettingStatus.Enabled
        }
    }

    @Test
    fun testMutableLocationSettingStateNoSensor() {
        val settingHelper = mockk<SettingHelper>()
        every { settingHelper.getSettings() } returns LocationSettingStatus.NoSensor

        composeTestRule.setContent {
            val locationSettingState = rememberMutableLocationState(settingHelper) {}

            locationSettingState.status shouldBe LocationSettingStatus.NoSensor
        }
    }
}
