package org.chapp.findfin.feature.bank.navigation

import androidx.compose.runtime.Composable
import org.chapp.findfin.core.navigation.BottomNavigationTab
import org.chapp.findfin.feature.bank.ui.R
import org.chapp.findfin.feature.bank.ui.banklist.view.BankListScreen

@Composable
fun BankDestination() {
    BankListScreen()
}

val bankBottomTabDestination =
    BottomNavigationTab(
        route = "home-bottom-banks",
        iconDrawableResource = R.drawable.bank_ic_list,
        textStringResource = R.string.bank_title_bank_list,
    )
