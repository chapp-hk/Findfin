package ch.app.hk.bank.locator.feature.auth.navigation.graph

import android.content.Context
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.navigation.compose.ComposeNavigator
import androidx.navigation.testing.TestNavHostController
import androidx.test.core.app.ApplicationProvider
import ch.app.hk.bank.locator.testing.instrument.ActivityResultTestRule
import ch.app.hk.bank.locator.testing.instrument.HiltComponentActivity
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import org.junit.Before
import org.junit.Rule

// TODO - investigate how to test the auth nav graphs
@HiltAndroidTest
class AuthOnboardingNavGraphTest {
    private val context = ApplicationProvider.getApplicationContext<Context>()

    @get:Rule(order = 0)
    val hiltTestRule = HiltAndroidRule(this)

    @get:Rule(order = 1)
    val composeTestRule = createAndroidComposeRule<HiltComponentActivity>()

    @get:Rule(order = 2)
    val activityResultTestRule = ActivityResultTestRule(context = context)

    private val navController = TestNavHostController(context)

    @Before
    fun setupAppNavHost() {
        hiltTestRule.inject()
        navController.navigatorProvider.addNavigator(ComposeNavigator())
    }
}
