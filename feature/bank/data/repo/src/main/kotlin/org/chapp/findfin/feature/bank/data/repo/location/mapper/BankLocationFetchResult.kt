package org.chapp.findfin.feature.bank.data.repo.location.mapper

sealed interface BankLocationFetchResult {
    data object HasNext : BankLocationFetchResult

    data object End : BankLocationFetchResult

    data object Error : BankLocationFetchResult
}
