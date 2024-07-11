package ch.app.hk.bank.locator.core.location.launcher.permission

import android.content.Context
import android.net.Uri
import android.provider.Settings
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
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import ch.app.hk.bank.locator.core.location.impl.helper.permission.PermissionHelper
import ch.app.hk.bank.locator.testing.instrument.HiltComponentActivity
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import io.kotest.matchers.shouldBe
import io.mockk.mockk
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
@HiltAndroidTest
class LocationPermissionLauncherTest {
    @get:Rule(order = 0)
    val hiltTestRule = HiltAndroidRule(this)

    @get:Rule(order = 1)
    val composeTestRule = createAndroidComposeRule<HiltComponentActivity>()

    @Before
    fun setUp() {
        hiltTestRule.inject()
    }

    @Test
    fun testLocationPermissionIntentStarted() {
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
    }

    @Test
    fun testLocationPermissionCreateIntent() {
        val mockPermissionHelper = mockk<PermissionHelper>()
        val context = ApplicationProvider.getApplicationContext<Context>().applicationContext
        val resultContract = LocationPermissionResultContract(mockPermissionHelper)

        val intent =
            resultContract.createIntent(
                context = context,
                input = Unit,
            )

        intent.action shouldBe Settings.ACTION_APPLICATION_DETAILS_SETTINGS
        intent.data shouldBe Uri.fromParts("package", context.packageName, null)
    }
}
