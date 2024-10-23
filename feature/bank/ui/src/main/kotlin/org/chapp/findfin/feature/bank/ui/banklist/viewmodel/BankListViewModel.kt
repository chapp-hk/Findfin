package org.chapp.findfin.feature.bank.ui.banklist.viewmodel

import org.chapp.findfin.core.design.ui.foundation.ScreenStateFlow

interface BankListViewModel {
    val screenState: ScreenStateFlow<List<String>, Nothing>
}
