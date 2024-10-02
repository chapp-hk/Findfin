package ch.app.hk.bank.locator.core.location.setting

import android.content.Context
import androidx.activity.ComponentActivity
import androidx.activity.compose.LocalActivityResultRegistryOwner
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.test.core.app.ApplicationProvider
import ch.app.hk.bank.locator.testing.instrument.ActivityResultTestRule
import io.mockk.mockk
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
        // TODO: Fix this test
        val registryOwner = activityResultTestRule.registryOwner(LocationSettingStatus.NoSensor)

        composeTestRule.setContent {
            CompositionLocalProvider(LocalActivityResultRegistryOwner provides registryOwner) {
                val onLocationStateResult = mockk<(LocationSettingStatus) -> Unit>()
                val locationSettingState =
                    rememberLocationSettingState(onResult = onLocationStateResult)

                locationSettingState.launchEnableLocation()

                // verify { onLocationStateResult(LocationSettingStatus.Enabled) }
            }
        }
    }
}
