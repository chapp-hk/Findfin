package org.chapp.findfin.feature.home.navigation

import kotlinx.serialization.Serializable
import org.chapp.findfin.core.navigation.BottomNavigationTab
import org.chapp.findfin.feature.home.ui.R

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
