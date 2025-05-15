package org.chapp.findfin.feature.bank.data.repo.model

sealed interface BankFetchResult {
    data object HasNext : BankFetchResult

    data object End : BankFetchResult

    data object Error : BankFetchResult
}
