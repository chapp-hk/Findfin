package ch.app.hk.bank.locator.feature.bank.data.remote.location.datasource

import ch.app.hk.bank.locator.feature.bank.data.remote.location.api.LocatorPath
import ch.app.hk.bank.locator.feature.bank.data.remote.location.model.LocatorResult

interface BankLocationRemoteDataSource {
    suspend fun getLocators(
        path: LocatorPath,
        language: String,
        pageSize: Int,
        offset: Int,
    ): LocatorResult
}
