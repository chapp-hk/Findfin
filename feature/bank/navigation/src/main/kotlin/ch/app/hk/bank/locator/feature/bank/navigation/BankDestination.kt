package ch.app.hk.bank.locator.feature.bank.navigation

import androidx.compose.runtime.Composable
import ch.app.hk.bank.locator.core.navigation.BottomNavigationTab
import ch.app.hk.bank.locator.feature.bank.ui.R
import ch.app.hk.bank.locator.feature.bank.ui.banklist.view.BankListScreen

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
