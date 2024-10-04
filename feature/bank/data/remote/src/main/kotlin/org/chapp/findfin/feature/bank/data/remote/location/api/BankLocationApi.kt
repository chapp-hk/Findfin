package org.chapp.findfin.feature.bank.data.remote.location.api

import org.chapp.findfin.feature.bank.data.remote.location.model.BankLocationResponse
import org.chapp.findfin.feature.bank.data.remote.location.model.Response

internal interface BankLocationApi {
    suspend fun getLocations(
        path: String,
        lang: String,
        pageSize: Int,
        offset: Int,
    ): Response<BankLocationResponse>
}
