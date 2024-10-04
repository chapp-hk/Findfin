package org.chapp.findfin.core.location

import android.app.Activity
import android.content.Context
import androidx.activity.ComponentActivity
import androidx.activity.compose.LocalActivityResultRegistryOwner
import androidx.activity.result.ActivityResult
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.test.core.app.ApplicationProvider
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.chapp.findfin.core.location.setting.helper.LocalSettingHelper
import org.chapp.findfin.core.location.setting.helper.SettingHelper
import org.chapp.findfin.core.location.setting.state.LocationSettingState
import org.chapp.findfin.core.location.setting.state.LocationSettingStatus
import org.chapp.findfin.testing.instrument.ActivityResultTestRule
import org.junit.Rule
import org.junit.Test

class LocationSettingCheckContentTest {
    private val context = ApplicationProvider.getApplicationContext<Context>()

    @get:Rule
    val activityResultTestRule = ActivityResultTestRule(context)

    @get:Rule
    val composeTestRule = createAndroidComposeRule<ComponentActivity>()

    @Test
    fun testLocationSettingCheckContent_onResult() {
        val settingHelper =
            mockk<SettingHelper>(relaxed = true) {
                coEvery { getIntentSenderRequest() } returns mockk(relaxed = true)
                every { getSettings() } returns LocationSettingStatus.Enabled
            }
        val registryOwner =
            activityResultTestRule.registryOwner(
                ActivityResult(
                    resultCode = Activity.RESULT_OK,
                    data = null,
                ),
            )
        val mockedOnResult = mockk<(LocationSettingState) -> Unit>(relaxed = true)

        composeTestRule.setContent {
            CompositionLocalProvider(
                values =
                    arrayOf(
                        LocalActivityResultRegistryOwner provides registryOwner,
                        LocalSettingHelper provides settingHelper,
                    ),
            ) {
                LocationSettingCheckContent(
                    onResult = mockedOnResult,
                ) { locationSettingState ->
                    Button(
                        onClick = { locationSettingState.launchEnableLocation() },
                    ) {
                        Text(text = "test")
                    }
                }
            }
        }

        composeTestRule.onNodeWithText("test").performClick()

        verify { mockedOnResult(any()) }
    }

    @Test
    fun testLocationSettingCheckContent_content() {
        val settingHelper =
            mockk<SettingHelper>(relaxed = true) {
                every { getSettings() } returns LocationSettingStatus.Enabled
            }

        composeTestRule.setContent {
            CompositionLocalProvider(
                value = LocalSettingHelper provides settingHelper,
            ) {
                LocationSettingCheckContent(onResult = {}) { locationSettingState ->
                    Text(text = locationSettingState.status.toString())
                }
            }
        }

        composeTestRule.onNodeWithText("Enabled").assertIsDisplayed()
    }
}
