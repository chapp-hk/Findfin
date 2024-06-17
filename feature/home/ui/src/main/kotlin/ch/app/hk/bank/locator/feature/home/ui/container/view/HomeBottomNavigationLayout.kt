package ch.app.hk.bank.locator.feature.home.ui.container.view

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.res.stringResource
import androidx.navigation.compose.rememberNavController
import ch.app.hk.bank.locator.core.navigation.BottomNavigationLayout
import ch.app.hk.bank.locator.core.navigation.BottomNavigationTab
import ch.app.hk.bank.locator.core.navigation.routeToBottomNavigationTab
import ch.app.hk.bank.locator.feature.home.ui.R
import ch.app.hk.bank.locator.feature.locator.ui.MapScreen

@Composable
fun HomeBottomNavigationLayout(onRequestAuth: () -> Unit) {
    val tabList =
        remember {
            listOf(
                homeTab,
                banksTab,
                mapTab,
                settingTab,
            )
        }

    val bottomNavController = rememberNavController()

    BottomNavigationLayout(
        navController = bottomNavController,
        bottomTabItems = tabList,
    ) { tab ->
        when (tab) {
            is HomeBottomTab.Banks -> {
                Text(text = stringResource(id = tab.textStringResource))
            }

            is HomeBottomTab.Home -> {
                HomeContainer(
                    onRequestAuth = onRequestAuth,
                    onSearch = {
                        // TODO - pass searchString to target tab
                        bottomNavController.routeToBottomNavigationTab(mapTab)
                    },
                )
            }

            is HomeBottomTab.Map -> {
                MapScreen()
            }

            is HomeBottomTab.Setting -> {
                Text(text = stringResource(id = tab.textStringResource))
            }
        }
    }
}

private val homeTab = HomeBottomTab.Home()
private val banksTab = HomeBottomTab.Banks()
private val mapTab = HomeBottomTab.Map()
private val settingTab = HomeBottomTab.Setting()

private sealed interface HomeBottomTab : BottomNavigationTab {
    data class Home(
        override val route: String = "home-bottom-home",
        override val iconDrawableResource: Int = R.drawable.home_ic_home,
        override val textStringResource: Int = R.string.home_tab_home,
    ) : HomeBottomTab

    data class Banks(
        override val route: String = "home-bottom-banks",
        override val iconDrawableResource: Int = R.drawable.home_ic_list,
        override val textStringResource: Int = R.string.home_tab_list,
    ) : HomeBottomTab

    data class Map(
        override val route: String = "home-bottom-map",
        override val iconDrawableResource: Int = R.drawable.home_ic_map,
        override val textStringResource: Int = R.string.home_tab_map,
    ) : HomeBottomTab

    data class Setting(
        override val route: String = "home-bottom-setting",
        override val iconDrawableResource: Int = R.drawable.home_ic_setting,
        override val textStringResource: Int = R.string.home_tab_setting,
    ) : HomeBottomTab
}
