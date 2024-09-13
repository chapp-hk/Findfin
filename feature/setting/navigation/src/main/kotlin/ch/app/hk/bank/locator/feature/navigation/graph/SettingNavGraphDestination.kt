package ch.app.hk.bank.locator.feature.navigation.graph

import androidx.compose.runtime.Composable
import ch.app.hk.bank.locator.core.navigation.BottomNavigationTab
import ch.app.hk.bank.locator.feature.setting.ui.R
import ch.app.hk.bank.locator.feature.setting.ui.list.view.SettingListScreen

@Composable
fun SettingDestination() {
    SettingListScreen()
}

val settingBottomTabDestination =
    BottomNavigationTab(
        route = "home-bottom-setting",
        iconDrawableResource = R.drawable.setting_ic_setting,
        textStringResource = R.string.setting_tab_setting,
    )
