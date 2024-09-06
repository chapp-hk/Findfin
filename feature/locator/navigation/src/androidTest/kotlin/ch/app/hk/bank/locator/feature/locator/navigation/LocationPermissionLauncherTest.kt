package ch.app.hk.bank.locator.feature.locator.navigation

import android.content.Context
import android.net.Uri
import android.provider.Settings
import androidx.activity.compose.LocalActivityResultRegistryOwner
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import ch.app.hk.bank.locator.testing.instrument.ActivityResultTestRule
import ch.app.hk.bank.locator.testing.instrument.HiltComponentActivity
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import io.kotest.matchers.shouldBe
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
@HiltAndroidTest
class LocationPermissionLauncherTest {
    private val context = ApplicationProvider.getApplicationContext<Context>()

    @get:Rule(order = 0)
    val hiltTestRule = HiltAndroidRule(this)

    @get:Rule(order = 1)
    val composeTestRule = createAndroidComposeRule<HiltComponentActivity>()

    @get:Rule(order = 2)
    val activityResultTestRule = ActivityResultTestRule(context)

    @Before
    fun setUp() {
        hiltTestRule.inject()
    }

    @Test
    fun testLocationPermissionIntentStarted() {
        val registryOwner = activityResultTestRule.registryOwner(mockedActivityResult = true)

        composeTestRule.setContent {
            CompositionLocalProvider(LocalActivityResultRegistryOwner provides registryOwner) {
                val launcher = rememberLocationPermissionLauncher { }

                Button(
                    onClick = {
                        launcher.launch(Unit)
                    },
                ) {
                    Text(text = "launch")
                }
            }
        }

        composeTestRule.onNodeWithText("launch").performClick()

        activityResultTestRule.launchedIntent.let {
            it!!.action shouldBe Settings.ACTION_APPLICATION_DETAILS_SETTINGS
            it.data shouldBe Uri.fromParts("package", context.packageName, null)
        }
    }
}
