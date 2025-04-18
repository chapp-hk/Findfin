package org.chapp.findfin.feature.bank.data.remote.network.model

sealed interface BankResult {
    data class Success(val data: List<BankResponse>) : BankResult

    data object Error : BankResult
}
