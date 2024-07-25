package ch.app.hk.bank.locator.feature.home.ui.nearby.view

import androidx.activity.compose.LocalActivityResultRegistryOwner
import androidx.activity.result.ActivityResultRegistry
import androidx.activity.result.ActivityResultRegistryOwner
import androidx.activity.result.contract.ActivityResultContract
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.core.app.ActivityOptionsCompat
import ch.app.hk.bank.locator.feature.home.ui.R
import ch.app.hk.bank.locator.testing.instrument.HiltComponentActivity
import ch.app.hk.bank.locator.testing.instrument.getResourceString
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import io.mockk.Called
import io.mockk.Runs
import io.mockk.every
import io.mockk.just
import io.mockk.mockk
import io.mockk.verify
import org.junit.Rule
import org.junit.Test

@HiltAndroidTest
class NearByLocationDisabledResultTest {
    @get:Rule(order = 0)
    val hiltTestRule = HiltAndroidRule(this)

    @get:Rule(order = 1)
    val composeTestRule = createAndroidComposeRule<HiltComponentActivity>()

    @Test
    fun clickActionButton_ShouldInvoke_OnLocationServiceEnabled() {
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
                            dispatchResult(requestCode, true)
                        }
                    }
            }

        val onLocationServiceEnabled = mockk<() -> Unit>()
        every { onLocationServiceEnabled() } just Runs

        composeTestRule.setContent {
            CompositionLocalProvider(LocalActivityResultRegistryOwner provides registryOwner) {
                NearByLocationDisabledResult(onLocationServiceEnabled = onLocationServiceEnabled)
            }
        }

        composeTestRule
            .onNodeWithText(getResourceString(R.string.home_button_location_settings))
            .performClick()

        verify { onLocationServiceEnabled() }
    }

    @Test
    fun clickActionButton_ShouldNotInvoke_OnLocationServiceEnabled() {
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
                            dispatchResult(requestCode, false)
                        }
                    }
            }

        val onLocationServiceEnabled = mockk<() -> Unit>()

        composeTestRule.setContent {
            CompositionLocalProvider(LocalActivityResultRegistryOwner provides registryOwner) {
                NearByLocationDisabledResult(onLocationServiceEnabled = onLocationServiceEnabled)
            }
        }

        composeTestRule
            .onNodeWithText(getResourceString(R.string.home_button_location_settings))
            .performClick()

        verify { onLocationServiceEnabled wasNot Called }
    }
}
