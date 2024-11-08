package org.chapp.findfin.feature.presentation.navigation.graph

import androidx.compose.runtime.Composable
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import org.chapp.findfin.core.navigation.BottomNavigationTab
import org.chapp.findfin.feature.setting.ui.R
import org.chapp.findfin.feature.setting.ui.list.view.SettingListScreen

/**
 * Composable function that represents the setting destination.
 * This function displays the setting list screen.
 */
@Composable
fun SettingDestination() {
    SettingListScreen()
}

/**
 * Serializable class representing a bottom navigation tab for the setting feature.
 *
 * @property route The route associated with the tab.
 * @property iconDrawableResource The drawable resource ID for the tab's icon.
 * @property textStringResource The string resource ID for the tab's text.
 */
@Serializable
class SettingBottomTabDestination(
    @SerialName("route")
    override val route: String = "home-bottom-setting",
    @SerialName("iconDrawableResource")
    override val iconDrawableResource: Int = R.drawable.setting_ic_setting,
    @SerialName("textStringResource")
    override val textStringResource: Int = R.string.setting_tab_setting,
) : BottomNavigationTab
