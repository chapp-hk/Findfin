package ch.app.hk.bank.locator.feature.locator.navigation

import androidx.compose.runtime.Composable
import ch.app.hk.bank.locator.core.navigation.BottomNavigationTab
import ch.app.hk.bank.locator.feature.locator.ui.R
import ch.app.hk.bank.locator.feature.locator.ui.map.view.MapScreen

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
