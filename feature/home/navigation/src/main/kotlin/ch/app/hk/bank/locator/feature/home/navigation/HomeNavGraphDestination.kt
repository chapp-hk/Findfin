package ch.app.hk.bank.locator.feature.home.navigation

import ch.app.hk.bank.locator.core.navigation.BottomNavigationTab
import ch.app.hk.bank.locator.feature.home.ui.R
import kotlinx.serialization.Serializable

@Serializable
object HomeNavGraphDestination

@Serializable
object HomeDestination

val homeBottomTabDestination =
    BottomNavigationTab(
        route = "home-bottom-home",
        iconDrawableResource = R.drawable.home_ic_home,
        textStringResource = R.string.home_tab_home,
    )

// TODO - move to setting module
val settingBottomTabDestination =
    BottomNavigationTab(
        route = "home-bottom-setting",
        iconDrawableResource = R.drawable.home_ic_setting,
        textStringResource = R.string.home_tab_setting,
    )
