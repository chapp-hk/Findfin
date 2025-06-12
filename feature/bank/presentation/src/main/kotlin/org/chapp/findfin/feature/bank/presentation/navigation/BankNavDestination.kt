package org.chapp.findfin.feature.bank.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import kotlinx.serialization.Serializable
import org.chapp.findfin.core.navigation.BottomNavigationTab
import org.chapp.findfin.feature.bank.presentation.R
import org.chapp.findfin.feature.bank.presentation.ui.banklist.view.BankListScreen

/**
 * Composable function that represents the bank destination.
 * This function displays the bank list screen.
 */
@Composable
fun BankDestination(modifier: Modifier = Modifier) {
    BankListScreen(modifier = modifier)
}

/**
 * Serializable class representing a bottom navigation tab for the bank feature.
 *
 * @property route The route associated with the tab.
 * @property iconDrawableResource The drawable resource ID for the tab's icon.
 * @property textStringResource The string resource ID for the tab's text.
 */
@Serializable
class BankBottomTabDestination(
    override val route: String = "home-bottom-bank",
    override val iconDrawableResource: Int = R.drawable.bank_ic_list,
    override val textStringResource: Int = R.string.bank_title_bank_list,
) : BottomNavigationTab
