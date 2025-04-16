package org.chapp.findfin.feature.bank.data.remote.network.model

sealed interface LocationResult {
    data class Success(val data: List<BankLocationResponse>) : LocationResult

    data object Error : LocationResult
}
