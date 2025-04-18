package org.chapp.findfin.feature.bank.data.repo.datasource.remote.model

sealed interface BankRemoteResult {
    data class Success(val data: List<BankRemote>) : BankRemoteResult

    data object Error : BankRemoteResult
}
