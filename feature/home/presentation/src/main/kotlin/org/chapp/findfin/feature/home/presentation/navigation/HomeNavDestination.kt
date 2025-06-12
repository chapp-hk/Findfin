package org.chapp.findfin.feature.home.presentation.navigation

import kotlinx.serialization.Serializable
import org.chapp.findfin.core.navigation.BottomNavigationTab
import org.chapp.findfin.feature.home.presentation.R

/**
 * Object representing the home navigation graph destination.
 */
@Serializable
object HomeNavGraphDestination

/**
 * Object representing the home destination.
 */
@Serializable
object HomeDestination

/**
 * Serializable class representing a bottom navigation tab for the home feature.
 *
 * @property route The route associated with the tab.
 * @property iconDrawableResource The drawable resource ID for the tab's icon.
 * @property textStringResource The string resource ID for the tab's text.
 */
@Serializable
class HomeBottomTabDestination(
    override val route: String = "home-bottom-home",
    override val iconDrawableResource: Int = R.drawable.home_ic_home,
    override val textStringResource: Int = R.string.home_tab_home,
) : BottomNavigationTab
