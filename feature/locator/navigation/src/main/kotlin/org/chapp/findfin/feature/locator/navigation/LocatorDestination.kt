package org.chapp.findfin.feature.locator.navigation

import androidx.compose.runtime.Composable
import org.chapp.findfin.core.navigation.BottomNavigationTab
import org.chapp.findfin.feature.locator.ui.R
import org.chapp.findfin.feature.locator.ui.map.view.MapScreen

/**
 * Composable function that displays the map screen.
 */
@Composable
fun MapDestination() {
    MapScreen()
}

/**
 * Represents the bottom navigation tab for the map screen.
 */
val mapBottomTabDestination =
    BottomNavigationTab(
        route = "home-bottom-map",
        iconDrawableResource = R.drawable.locator_ic_map,
        textStringResource = R.string.locator_tab_map,
    )
