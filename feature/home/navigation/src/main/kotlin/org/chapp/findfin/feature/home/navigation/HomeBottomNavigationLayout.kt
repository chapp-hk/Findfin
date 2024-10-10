package org.chapp.findfin.feature.home.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import org.chapp.findfin.core.navigation.BottomNavigationLayout
import org.chapp.findfin.feature.bank.navigation.BankBottomTabDestination
import org.chapp.findfin.feature.bank.navigation.BankDestination
import org.chapp.findfin.feature.home.ui.container.view.HomeContainer
import org.chapp.findfin.feature.locator.navigation.MapBottomTabDestination
import org.chapp.findfin.feature.locator.navigation.MapDestination
import org.chapp.findfin.feature.navigation.graph.SettingBottomTabDestination
import org.chapp.findfin.feature.navigation.graph.SettingDestination

/**
 * Composable function that sets up the home bottom navigation layout.
 *
 * @param onRequestAuth A callback function that is invoked to request authentication.
 */
@Composable
fun HomeBottomNavigationLayout(onRequestAuth: () -> Unit) {
    val bottomNavController = rememberNavController()

    BottomNavigationLayout(
        navController = bottomNavController,
        bottomTabItems = bottomTabList,
    ) {
        composable<HomeBottomTabDestination> {
            HomeContainer(
                onRequestAuth = onRequestAuth,
                onSearch = {
                    bottomNavController.navigate(MapBottomTabDestination(searchKeyword = it)) {
                        // Pop up to the start destination of the graph to
                        // avoid building up a large stack of destinations
                        // on the back stack as users select items
                        popUpTo(bottomTabList.first()) { saveState = true }
                        // Avoid multiple copies of the same destination when
                        // reselecting the same item
                        launchSingleTop = true
                        // Restore state when reselecting a previously selected item
                        restoreState = true
                    }
                },
            )
        }

        composable<BankBottomTabDestination> {
            BankDestination()
        }

        composable<MapBottomTabDestination> {
            MapDestination()
        }

        composable<SettingBottomTabDestination> {
            SettingDestination()
        }
    }
}

private val bottomTabList =
    listOf(
        HomeBottomTabDestination(),
        BankBottomTabDestination(),
        MapBottomTabDestination(),
        SettingBottomTabDestination(),
    )
