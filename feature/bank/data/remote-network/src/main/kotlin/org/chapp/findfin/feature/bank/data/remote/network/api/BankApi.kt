package org.chapp.findfin.feature.bank.data.remote.network.api

import org.chapp.findfin.feature.bank.data.remote.network.model.BankResponse
import org.chapp.findfin.feature.bank.data.remote.network.model.Response

internal interface BankApi {
    suspend fun getLocations(
        path: String,
        lang: String,
        pageSize: Int,
        offset: Int,
    ): Response<BankResponse>
}
