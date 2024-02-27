package ch.app.hk.bank.locator.core.navigation

import androidx.activity.ComponentActivity
import androidx.compose.material3.Text
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithTag
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

    sealed interface TestBottomTab : BottomNavigationTab {
        data class Home(
            override val route: String = "test-bottom-home",
            override val iconDrawableResource: Int = R.drawable.navigation_test_ic_home,
            override val textStringResource: Int = R.string.navigation_test_home,
        ) : TestBottomTab

        data class Setting(
            override val route: String = "test-bottom-setting",
            override val iconDrawableResource: Int = R.drawable.navigation_test_ic_setting,
            override val textStringResource: Int = R.string.navigation_test_setting,
        ) : TestBottomTab
    }

    @Before
    fun setUp() {
        composeTestRule.setContent {
            navController = TestNavHostController(LocalContext.current)
            navController.navigatorProvider.addNavigator(ComposeNavigator())

            BottomNavigationLayout(
                navController = navController,
                bottomTabItems =
                    listOf(
                        TestBottomTab.Home(),
                        TestBottomTab.Setting(),
                    ),
            ) { tab ->
                when (tab) {
                    is TestBottomTab.Home -> Text(text = tab.route)
                    is TestBottomTab.Setting -> Text(text = tab.route)
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
            .onNodeWithTag("test-bottom-setting")
            .performClick()

        assert(navController.currentDestination?.route == "test-bottom-setting")
    }
}
