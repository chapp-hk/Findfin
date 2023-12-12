package ch.app.hk.bank.locator.feature.locator.data.remote.api

import ch.app.hk.bank.locator.feature.locator.data.remote.response.Bank
import ch.app.hk.bank.locator.feature.locator.data.remote.response.Response

internal interface LocatorApi {
    suspend fun getBanks(
        type: String,
        lang: String,
        pageSize: Int,
        offset: Int,
    ): Response<Bank>
}
