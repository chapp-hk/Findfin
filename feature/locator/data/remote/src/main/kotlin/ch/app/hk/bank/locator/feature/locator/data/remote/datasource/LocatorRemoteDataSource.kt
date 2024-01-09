package ch.app.hk.bank.locator.feature.locator.data.remote.datasource

import ch.app.hk.bank.locator.feature.locator.data.remote.api.LocatorType
import ch.app.hk.bank.locator.feature.locator.data.remote.response.LocatorResponse

interface LocatorRemoteDataSource {
    suspend fun getLocators(
        type: LocatorType,
        language: String,
        pageSize: Int,
        offset: Int,
    ): List<LocatorResponse>
}
