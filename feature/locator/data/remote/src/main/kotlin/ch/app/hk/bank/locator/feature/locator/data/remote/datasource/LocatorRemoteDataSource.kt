package ch.app.hk.bank.locator.feature.locator.data.remote.datasource

import ch.app.hk.bank.locator.feature.locator.data.remote.api.LocatorPath
import ch.app.hk.bank.locator.feature.locator.data.remote.model.LocatorResult

interface LocatorRemoteDataSource {
    suspend fun getLocators(
        path: LocatorPath,
        language: String,
        pageSize: Int,
        offset: Int,
    ): LocatorResult
}
