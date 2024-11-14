package org.chapp.findfin.feature.locator.presentation.navigation

import androidx.compose.runtime.Composable
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import org.chapp.findfin.core.navigation.BottomNavigationTab
import org.chapp.findfin.feature.locator.presentation.R
import org.chapp.findfin.feature.locator.presentation.ui.map.view.MapScreen

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
@Serializable
class MapBottomTabDestination(
    @SerialName("route")
    override val route: String = "home-bottom-map",
    @SerialName("iconDrawableResource")
    override val iconDrawableResource: Int = R.drawable.locator_ic_map,
    @SerialName("textStringResource")
    override val textStringResource: Int = R.string.locator_tab_map,
    @SerialName("searchKeyword")
    val searchKeyword: String? = null,
) : BottomNavigationTab
