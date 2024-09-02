package ch.app.hk.bank.locator.feature.bank.data.remote.location.api

import ch.app.hk.bank.locator.feature.bank.data.remote.location.model.BankLocationResponse
import ch.app.hk.bank.locator.feature.bank.data.remote.location.model.Response

internal interface BankLocationApi {
    suspend fun getLocators(
        path: String,
        lang: String,
        pageSize: Int,
        offset: Int,
    ): Response<BankLocationResponse>
}
