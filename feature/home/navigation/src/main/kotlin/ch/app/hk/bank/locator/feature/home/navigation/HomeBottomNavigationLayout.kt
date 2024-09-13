package ch.app.hk.bank.locator.feature.home.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.rememberNavController
import ch.app.hk.bank.locator.core.navigation.BottomNavigationLayout
import ch.app.hk.bank.locator.core.navigation.routeToBottomNavigationTab
import ch.app.hk.bank.locator.feature.bank.navigation.BankDestination
import ch.app.hk.bank.locator.feature.bank.navigation.bankBottomTabDestination
import ch.app.hk.bank.locator.feature.home.ui.container.view.HomeContainer
import ch.app.hk.bank.locator.feature.locator.navigation.MapDestination
import ch.app.hk.bank.locator.feature.locator.navigation.mapBottomTabDestination
import ch.app.hk.bank.locator.feature.navigation.graph.SettingDestination
import ch.app.hk.bank.locator.feature.navigation.graph.settingBottomTabDestination

@Composable
fun HomeBottomNavigationLayout(onRequestAuth: () -> Unit) {
    val bottomNavController = rememberNavController()

    BottomNavigationLayout(
        navController = bottomNavController,
        bottomTabItems = bottomTabList,
    ) { tab ->
        when (tab) {
            bankBottomTabDestination -> {
                BankDestination()
            }

            homeBottomTabDestination -> {
                HomeContainer(
                    onRequestAuth = onRequestAuth,
                    onSearch = {
                        // TODO - pass searchString to target tab
                        bottomNavController.routeToBottomNavigationTab(bottomTabList[2])
                    },
                )
            }

            mapBottomTabDestination -> {
                MapDestination()
            }

            settingBottomTabDestination -> {
                SettingDestination()
            }
        }
    }
}

private val bottomTabList =
    listOf(
        homeBottomTabDestination,
        bankBottomTabDestination,
        mapBottomTabDestination,
        settingBottomTabDestination,
    )
