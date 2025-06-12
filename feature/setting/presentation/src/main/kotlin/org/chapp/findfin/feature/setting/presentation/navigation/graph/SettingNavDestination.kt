package org.chapp.findfin.feature.setting.presentation.navigation.graph

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import kotlinx.serialization.Serializable
import org.chapp.findfin.core.navigation.BottomNavigationTab
import org.chapp.findfin.feature.setting.presentation.R
import org.chapp.findfin.feature.setting.presentation.ui.list.view.SettingListScreen

/**
 * Composable function that represents the setting destination.
 * This function displays the setting list screen.
 */
@Composable
fun SettingDestination(modifier: Modifier = Modifier) {
    SettingListScreen(modifier = modifier)
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
    override val route: String = "home-bottom-setting",
    override val iconDrawableResource: Int = R.drawable.setting_ic_setting,
    override val textStringResource: Int = R.string.setting_tab_setting,
) : BottomNavigationTab
