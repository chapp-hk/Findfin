package org.chapp.findfin.feature.bank.data.remote.network.datasource

import org.chapp.findfin.feature.bank.data.remote.network.api.LocationPath
import org.chapp.findfin.feature.bank.data.remote.network.model.LocationResult

interface BankLocationRemoteDataSource {
    suspend fun getLocations(
        path: LocationPath,
        language: String,
        pageSize: Int,
        offset: Int,
    ): LocationResult
}
