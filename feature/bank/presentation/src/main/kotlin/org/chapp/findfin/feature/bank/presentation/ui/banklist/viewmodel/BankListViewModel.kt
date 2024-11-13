package org.chapp.findfin.feature.bank.presentation.ui.banklist.viewmodel

import org.chapp.findfin.core.design.ui.foundation.ScreenStateFlow

internal interface BankListViewModel {
    val screenState: ScreenStateFlow<List<String>, Nothing>
}
