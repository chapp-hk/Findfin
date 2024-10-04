package org.chapp.findfin.feature.bank.data.remote.location.model

data class LocatorApiError(
    val errorCode: String,
    val errorMessage: String,
) : Throwable()
