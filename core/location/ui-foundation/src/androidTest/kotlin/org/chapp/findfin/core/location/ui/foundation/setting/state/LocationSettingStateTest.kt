package org.chapp.findfin.core.location.ui.foundation.setting.state

import android.app.Activity
import android.content.Context
import androidx.activity.ComponentActivity
import androidx.activity.compose.LocalActivityResultRegistryOwner
import androidx.activity.result.ActivityResult
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.test.core.app.ApplicationProvider
import io.mockk.coEvery
import io.mockk.mockk
import io.mockk.verify
import org.chapp.findfin.core.location.ui.foundation.setting.helper.SettingHelper
import org.chapp.findfin.testing.instrument.ActivityResultTestRule
import org.junit.Rule
import org.junit.Test

class LocationSettingStateTest {
    private val context = ApplicationProvider.getApplicationContext<Context>()

    @get:Rule
    val activityResultTestRule = ActivityResultTestRule(context)

    @get:Rule
    val composeTestRule = createAndroidComposeRule<ComponentActivity>()

    @Test
    fun testLocationSettingStateLauncherResult() {
        val settingHelper =
            mockk<SettingHelper>(relaxed = true) {
                coEvery { getIntentSenderRequest() } returns mockk(relaxed = true)
            }
        val registryOwner =
            activityResultTestRule.registryOwner(
                ActivityResult(
                    resultCode = Activity.RESULT_OK,
                    data = null,
                ),
            )
        val onLocationStateResult = mockk<(LocationSettingState) -> Unit>(relaxed = true)

        composeTestRule.setContent {
            CompositionLocalProvider(
                value = LocalActivityResultRegistryOwner provides registryOwner,
            ) {
                val locationSettingState =
                    rememberLocationSettingState(
                        settingHelper = settingHelper,
                        onResult = onLocationStateResult,
                    )

                Button(
                    onClick = {
                        locationSettingState.launchEnableLocation()
                    },
                ) {
                    Text("Enable Location")
                }
            }
        }

        composeTestRule.onNodeWithText("Enable Location").performClick()

        verify { onLocationStateResult(any()) }
    }
}
