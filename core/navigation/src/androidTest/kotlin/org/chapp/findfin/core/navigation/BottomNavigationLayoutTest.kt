package org.chapp.findfin.core.navigation

import androidx.activity.ComponentActivity
import androidx.compose.material3.Text
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.performClick
import androidx.navigation.NavHostController
import androidx.navigation.compose.ComposeNavigator
import androidx.navigation.compose.composable
import androidx.navigation.testing.TestNavHostController
import androidx.navigation.toRoute
import io.kotest.matchers.types.shouldBeInstanceOf
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import org.chapp.findfin.core.navigation.test.R
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class BottomNavigationLayoutTest {
    @get:Rule
    val composeTestRule = createAndroidComposeRule<ComponentActivity>()

    private lateinit var navController: NavHostController

    @Serializable
    private class HomeTab(
        @SerialName("route")
        override val route: String = "test-bottom-home",
        @SerialName("iconDrawableResource")
        override val iconDrawableResource: Int = R.drawable.navigation_test_ic_home,
        @SerialName("textStringResource")
        override val textStringResource: Int = R.string.navigation_test_home,
    ) : BottomNavigationTab

    @Serializable
    private class SettingTab(
        @SerialName("route")
        override val route: String = "test-bottom-setting",
        @SerialName("iconDrawableResource")
        override val iconDrawableResource: Int = R.drawable.navigation_test_ic_setting,
        @SerialName("textStringResource")
        override val textStringResource: Int = R.string.navigation_test_setting,
    ) : BottomNavigationTab

    @Before
    fun setUp() {
        composeTestRule.setContent {
            navController = TestNavHostController(LocalContext.current)
            navController.navigatorProvider.addNavigator(ComposeNavigator())

            BottomNavigationLayout(
                navController = navController,
                bottomTabItems =
                    listOf(
                        HomeTab(),
                        SettingTab(),
                    ),
                onTabClick = {},
            ) {
                composable<HomeTab> {
                    Text(text = "Home")
                }

                composable<SettingTab> {
                    Text(text = "Setting")
                }
            }
        }
    }

    @Test
    fun assertInitialRoute() {
        navController
            .currentBackStackEntry
            ?.toRoute<HomeTab>()
            ?.shouldBeInstanceOf<HomeTab>()
    }

    @Test
    fun assertRouteToOtherTab() {
        composeTestRule
            .onNodeWithContentDescription("test-bottom-setting")
            .performClick()

        navController
            .currentBackStackEntry
            ?.toRoute<SettingTab>()
            ?.shouldBeInstanceOf<SettingTab>()
    }
}
