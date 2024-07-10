package ch.app.hk.bank.locator.core.location.launcher.setting

import androidx.activity.compose.LocalActivityResultRegistryOwner
import androidx.activity.result.ActivityResultRegistry
import androidx.activity.result.ActivityResultRegistryOwner
import androidx.activity.result.contract.ActivityResultContract
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.core.app.ActivityOptionsCompat
import androidx.test.ext.junit.runners.AndroidJUnit4
import ch.app.hk.bank.locator.core.location.impl.helper.hardware.GpsHelper
import ch.app.hk.bank.locator.testing.instrument.HiltComponentActivity
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import io.mockk.every
import io.mockk.mockk
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
        hiltTestRule.inject()
    }

    @Test
    fun testLocationSourceSettingsIntentStarted() {
        composeTestRule.setContent {
            val registryOwner =
                object : ActivityResultRegistryOwner {
                    override val activityResultRegistry: ActivityResultRegistry =
                        object : ActivityResultRegistry() {
                            override fun <I, O> onLaunch(
                                requestCode: Int,
                                contract: ActivityResultContract<I, O>,
                                input: I,
                                options: ActivityOptionsCompat?,
                            ) {
                                dispatchResult(0, true)
                            }
                        }
                }

            CompositionLocalProvider(LocalActivityResultRegistryOwner provides registryOwner) {
                val launcher = rememberLocationSourceSettingsLauncher { }

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
