package ch.app.hk.bank.locator.feature.locator.data.remote.datasource

import ch.app.hk.bank.locator.core.network.ApiResult
import ch.app.hk.bank.locator.feature.locator.data.remote.api.LocatorPath
import ch.app.hk.bank.locator.feature.locator.data.remote.response.LocatorResponse

interface LocatorRemoteDataSource {
    suspend fun getLocators(
        path: LocatorPath,
        language: String,
        pageSize: Int,
        offset: Int,
    ): ApiResult<List<LocatorResponse>>
}
