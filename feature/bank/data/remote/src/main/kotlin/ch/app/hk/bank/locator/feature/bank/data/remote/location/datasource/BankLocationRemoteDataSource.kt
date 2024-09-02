package ch.app.hk.bank.locator.feature.bank.data.remote.location.datasource

import ch.app.hk.bank.locator.feature.bank.data.remote.location.api.LocationPath
import ch.app.hk.bank.locator.feature.bank.data.remote.location.model.LocationResult

interface BankLocationRemoteDataSource {
    suspend fun getLocations(
        path: LocationPath,
        language: String,
        pageSize: Int,
        offset: Int,
    ): LocationResult
}
