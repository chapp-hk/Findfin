package org.chapp.findfin.feature.bank.data.repo.mapper

sealed interface BankFetchResult {
    data object HasNext : BankFetchResult

    data object End : BankFetchResult

    data object Error : BankFetchResult
}
