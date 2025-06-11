package org.chapp.findfin.feature.home.presentation.navigation

import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBars
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import org.chapp.findfin.core.navigation.BottomNavigationLayout
import org.chapp.findfin.core.navigation.BottomNavigationTab
import org.chapp.findfin.feature.bank.presentation.navigation.BankBottomTabDestination
import org.chapp.findfin.feature.bank.presentation.navigation.BankDestination
import org.chapp.findfin.feature.home.presentation.ui.container.view.HomeContainer
import org.chapp.findfin.feature.locator.presentation.navigation.MapBottomTabDestination
import org.chapp.findfin.feature.locator.presentation.navigation.MapDestination
import org.chapp.findfin.feature.setting.presentation.navigation.graph.SettingBottomTabDestination
import org.chapp.findfin.feature.setting.presentation.navigation.graph.SettingDestination

/**
 * Composable function that sets up the home bottom navigation layout.
 *
 * @param onRequestAuth A callback function that is invoked to request authentication.
 */
@Composable
fun HomeBottomNavigationLayout(onRequestAuth: () -> Unit) {
    val bottomNavController = rememberNavController()
    val topPadding = WindowInsets.statusBars.asPaddingValues().calculateTopPadding()

    BottomNavigationLayout(
        navController = bottomNavController,
        bottomTabItems = bottomTabList,
        onTabClick = { tab ->
            bottomNavController.navigateToBottomTab(tab)
        },
    ) {
        composable<HomeBottomTabDestination> {
            HomeContainer(
                modifier = Modifier.padding(top = topPadding),
                homeEvent =
                    HomeEvent(
                        onRequestAuth = onRequestAuth,
                        onSearch = { searchKeyword ->
                            val destination = MapBottomTabDestination(searchKeyword = searchKeyword)
                            bottomNavController.navigateToBottomTab(destination)
                        },
                        navigateToMap = {
                            bottomNavController.navigateToBottomTab(MapBottomTabDestination())
                        },
                    ),
            )
        }

        composable<BankBottomTabDestination> {
            BankDestination(Modifier.padding(top = topPadding))
        }

        composable<MapBottomTabDestination> {
            MapDestination(modifier = Modifier)
        }

        composable<SettingBottomTabDestination> {
            SettingDestination(modifier = Modifier.padding(top = topPadding))
        }
    }
}

private fun NavHostController.navigateToBottomTab(tab: BottomNavigationTab) {
    navigate(tab) {
        // Pop up to the start destination of the graph to
        // avoid building up a large stack of destinations
        // on the back stack as users select items
        popUpTo<HomeBottomTabDestination> { saveState = true }
        // Avoid multiple copies of the same destination when
        // reselecting the same item
        launchSingleTop = true
        // Restore state when reselecting a previously selected item
        restoreState = true
    }
}

private val bottomTabList =
    listOf(
        HomeBottomTabDestination(),
        BankBottomTabDestination(),
        MapBottomTabDestination(),
        SettingBottomTabDestination(),
    )
