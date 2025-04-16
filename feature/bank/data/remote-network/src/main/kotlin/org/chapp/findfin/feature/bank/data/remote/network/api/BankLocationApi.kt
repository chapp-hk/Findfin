package org.chapp.findfin.feature.bank.data.remote.network.api

import org.chapp.findfin.feature.bank.data.remote.network.model.BankLocationResponse
import org.chapp.findfin.feature.bank.data.remote.network.model.Response

internal interface BankLocationApi {
    suspend fun getLocations(
        path: String,
        lang: String,
        pageSize: Int,
        offset: Int,
    ): Response<BankLocationResponse>
}
