package org.chapp.findfin.feature.bank.presentation.ui.banklist.viewmodel

import kotlinx.coroutines.flow.StateFlow

internal interface BankListViewModel {
    val screenState: StateFlow<List<String>>
}
