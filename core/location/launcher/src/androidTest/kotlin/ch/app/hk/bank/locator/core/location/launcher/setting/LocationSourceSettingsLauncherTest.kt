package ch.app.hk.bank.locator.core.location.launcher.setting

import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.test.espresso.intent.Intents
import androidx.test.ext.junit.runners.AndroidJUnit4
import ch.app.hk.bank.locator.core.location.impl.helper.hardware.GpsHelper
import ch.app.hk.bank.locator.testing.instrument.HiltComponentActivity
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import io.mockk.every
import io.mockk.mockk
import org.junit.After
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
@HiltAndroidTest
class LocationSourceSettingsLauncherTest {
    @get:Rule(order = 0)
    val hiltTestRule = HiltAndroidRule(this)

    @get:Rule(order = 1)
    val composeTestRule = createAndroidComposeRule<HiltComponentActivity>()

    @Before
    fun setUp() {
        Intents.init()
    }

    @After
    fun tearDown() {
        Intents.release()
    }

    @Test
    fun testLocationSourceSettingsIntentStarted() {
        composeTestRule.setContent {
            val launcher = rememberLocationSourceSettingsLauncher { }

            Button(
                onClick = {
                    launcher.launch(Unit)
                },
            ) {
                Text(text = "launch")
            }
        }

        composeTestRule.onNodeWithText("launch").performClick()

//        intended(hasAction(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS))
    }

    @Test
    fun testLocationSourceSettingsResultContractParseResult() {
        val mockGpsHelper = mockk<GpsHelper>()
        val resultContract = LocationSourceSettingsResultContract(mockGpsHelper)

        every { mockGpsHelper.isGpsEnabled() } returns true
        assertTrue(resultContract.parseResult(0, null))

        every { mockGpsHelper.isGpsEnabled() } returns false
        assertFalse(resultContract.parseResult(0, null))
    }
}
