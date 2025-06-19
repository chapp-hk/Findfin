package org.chapp.findfin.feature.locator.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import kotlinx.serialization.Serializable
import org.chapp.findfin.core.navigation.BottomNavigationTab
import org.chapp.findfin.feature.locator.presentation.R
import org.chapp.findfin.feature.locator.presentation.ui.map.view.MapScreen

/**
 * Composable function that displays the map screen.
 */
@Composable
fun MapDestination(modifier: Modifier = Modifier) {
    MapScreen(modifier = modifier)
}

/**
 * Represents the bottom navigation tab for the map screen.
 */
@Serializable
class MapBottomTabDestination(
    override val route: String = "home-bottom-map",
    override val iconDrawableResource: Int = R.drawable.locator_ic_map,
    override val textStringResource: Int = R.string.locator_tab_map,
    val searchKeyword: String? = null,
    val searchType: MapSearchType? = null,
) : BottomNavigationTab

enum class MapSearchType {
    BRANCH,
    ATM,
}
