package org.chapp.findfin.feature.home.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.rememberNavController
import org.chapp.findfin.core.navigation.BottomNavigationLayout
import org.chapp.findfin.core.navigation.routeToBottomNavigationTab
import org.chapp.findfin.feature.bank.navigation.BankDestination
import org.chapp.findfin.feature.bank.navigation.bankBottomTabDestination
import org.chapp.findfin.feature.home.ui.container.view.HomeContainer
import org.chapp.findfin.feature.locator.navigation.MapDestination
import org.chapp.findfin.feature.locator.navigation.mapBottomTabDestination
import org.chapp.findfin.feature.navigation.graph.SettingDestination
import org.chapp.findfin.feature.navigation.graph.settingBottomTabDestination

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
