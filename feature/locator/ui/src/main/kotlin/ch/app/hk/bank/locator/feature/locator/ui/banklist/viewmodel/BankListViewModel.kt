package ch.app.hk.bank.locator.feature.locator.ui.banklist.viewmodel

import ch.app.hk.bank.locator.core.design.ui.ScreenStateFlow

interface BankListViewModel {
    val screenState: ScreenStateFlow<List<String>, Nothing>
}
