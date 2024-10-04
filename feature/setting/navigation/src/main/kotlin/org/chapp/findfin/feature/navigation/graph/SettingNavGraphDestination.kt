package org.chapp.findfin.feature.navigation.graph

import androidx.compose.runtime.Composable
import org.chapp.findfin.core.navigation.BottomNavigationTab
import org.chapp.findfin.feature.setting.ui.R
import org.chapp.findfin.feature.setting.ui.list.view.SettingListScreen

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
