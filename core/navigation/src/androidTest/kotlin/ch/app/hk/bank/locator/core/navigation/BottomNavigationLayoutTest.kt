package ch.app.hk.bank.locator.core.navigation

import androidx.activity.ComponentActivity
import androidx.compose.material3.Text
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.performClick
import androidx.navigation.NavHostController
import androidx.navigation.compose.ComposeNavigator
import androidx.navigation.testing.TestNavHostController
import ch.app.hk.bank.locator.core.navigation.test.R
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class BottomNavigationLayoutTest {
    @get:Rule
    val composeTestRule = createAndroidComposeRule<ComponentActivity>()

    private lateinit var navController: NavHostController

    private val homeTab =
        BottomNavigationTab(
            route = "test-bottom-home",
            iconDrawableResource = R.drawable.navigation_test_ic_home,
            textStringResource = R.string.navigation_test_home,
        )

    private val settingTab =
        BottomNavigationTab(
            route = "test-bottom-setting",
            iconDrawableResource = R.drawable.navigation_test_ic_setting,
            textStringResource = R.string.navigation_test_setting,
        )

    @Before
    fun setUp() {
        composeTestRule.setContent {
            navController = TestNavHostController(LocalContext.current)
            navController.navigatorProvider.addNavigator(ComposeNavigator())

            BottomNavigationLayout(
                navController = navController,
                bottomTabItems =
                    listOf(
                        homeTab,
                        settingTab,
                    ),
            ) { tab ->
                when (tab) {
                    homeTab -> Text(text = tab.route)
                    settingTab -> Text(text = tab.route)
                }
            }
        }
    }

    @Test
    fun assertInitialRoute() {
        assert(navController.currentDestination?.route == "test-bottom-home")
    }

    @Test
    fun assertRouteToOtherTab() {
        composeTestRule
            .onNodeWithContentDescription("test-bottom-setting")
            .performClick()

        assert(navController.currentDestination?.route == "test-bottom-setting")
    }
}
