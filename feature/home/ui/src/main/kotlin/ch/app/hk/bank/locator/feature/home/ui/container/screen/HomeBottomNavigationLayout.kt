package ch.app.hk.bank.locator.feature.home.ui.container.screen

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.res.stringResource
import ch.app.hk.bank.locator.core.navigation.BottomNavigationLayout
import ch.app.hk.bank.locator.core.navigation.BottomNavigationTab
import ch.app.hk.bank.locator.feature.home.ui.R

@Composable
fun HomeBottomNavigationLayout() {
    val tabList =
        remember {
            listOf(
                HomeBottomTab.Home(),
                HomeBottomTab.Banks(),
                HomeBottomTab.Map(),
                HomeBottomTab.Setting(),
            )
        }

    BottomNavigationLayout(bottomTabItems = tabList) { tab ->
        when (tab) {
            is HomeBottomTab.Banks -> Text(text = stringResource(id = tab.textStringResource))
            is HomeBottomTab.Home -> Text(text = stringResource(id = tab.textStringResource))
            is HomeBottomTab.Map -> Text(text = stringResource(id = tab.textStringResource))
            is HomeBottomTab.Setting -> Text(text = stringResource(id = tab.textStringResource))
        }
    }
}

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
