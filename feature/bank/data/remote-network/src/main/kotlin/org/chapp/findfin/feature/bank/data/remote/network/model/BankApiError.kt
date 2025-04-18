package org.chapp.findfin.feature.bank.data.remote.network.model

data class BankApiError(
    val errorCode: String,
    val errorMessage: String,
) : Throwable()
