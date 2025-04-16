package org.chapp.findfin.feature.bank.data.remote.network.model

data class LocatorApiError(
    val errorCode: String,
    val errorMessage: String,
) : Throwable()
