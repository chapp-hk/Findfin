package ch.app.hk.bank.locator.feature.locator.data.remote.api

import ch.app.hk.bank.locator.feature.locator.data.remote.model.LocatorResponse
import ch.app.hk.bank.locator.feature.locator.data.remote.model.Response

internal interface LocatorApi {
    suspend fun getLocators(
        path: String,
        lang: String,
        pageSize: Int,
        offset: Int,
    ): Response<LocatorResponse>
}
