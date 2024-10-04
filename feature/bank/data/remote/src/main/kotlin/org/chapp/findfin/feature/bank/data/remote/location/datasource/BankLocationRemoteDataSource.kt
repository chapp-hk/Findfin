package org.chapp.findfin.feature.bank.data.remote.location.datasource

import org.chapp.findfin.feature.bank.data.remote.location.api.LocationPath
import org.chapp.findfin.feature.bank.data.remote.location.model.LocationResult

interface BankLocationRemoteDataSource {
    suspend fun getLocations(
        path: LocationPath,
        language: String,
        pageSize: Int,
        offset: Int,
    ): LocationResult
}
