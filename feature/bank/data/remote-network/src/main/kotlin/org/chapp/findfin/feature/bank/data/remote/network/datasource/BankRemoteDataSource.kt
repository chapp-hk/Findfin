package org.chapp.findfin.feature.bank.data.remote.network.datasource

import org.chapp.findfin.feature.bank.data.remote.network.api.TypePath
import org.chapp.findfin.feature.bank.data.remote.network.model.BankResult

interface BankRemoteDataSource {
    suspend fun getLocations(
        path: TypePath,
        language: String,
        pageSize: Int,
        offset: Int,
    ): BankResult
}
