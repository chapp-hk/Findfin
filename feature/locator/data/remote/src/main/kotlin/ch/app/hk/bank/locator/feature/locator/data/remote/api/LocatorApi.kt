package ch.app.hk.bank.locator.feature.locator.data.remote.api

import ch.app.hk.bank.locator.feature.locator.data.remote.response.LocatorResponse
import ch.app.hk.bank.locator.feature.locator.data.remote.response.Response

internal interface LocatorApi {
    suspend fun getLocators(
        type: String,
        lang: String,
        pageSize: Int,
        offset: Int,
    ): Response<LocatorResponse>
}
