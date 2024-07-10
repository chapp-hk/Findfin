package ch.app.hk.bank.locator.core.location.launcher.permission

import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.test.espresso.intent.Intents
import ch.app.hk.bank.locator.core.location.impl.helper.permission.PermissionHelper
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

@HiltAndroidTest
class LocationPermissionLauncherTest {
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
    fun testLocationPermissionIntentStarted() {
        composeTestRule.setContent {
            val launcher = rememberLocationPermissionLauncher { }

            Button(
                onClick = {
                    launcher.launch(Unit)
                },
            ) {
                Text(text = "launch")
            }
        }

        composeTestRule.onNodeWithText("launch").performClick()

//        intended(
//            allOf(
//                hasAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS),
//                hasData(
//                    Uri.fromParts(
//                        "package",
//                        ApplicationProvider.getApplicationContext<Context>().packageName,
//                        null,
//                    ),
//                ),
//            ),
//        )
    }

    @Test
    fun testLocationPermissionResultContractParseResult() {
        val mockPermissionHelper = mockk<PermissionHelper>()
        val resultContract = LocationPermissionResultContract(mockPermissionHelper)

        every { mockPermissionHelper.checkPermission() } returns true
        assertTrue(resultContract.parseResult(0, null))

        every { mockPermissionHelper.checkPermission() } returns false
        assertFalse(resultContract.parseResult(0, null))
    }
}
