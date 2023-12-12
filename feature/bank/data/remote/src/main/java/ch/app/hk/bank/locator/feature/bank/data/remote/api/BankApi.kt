package ch.app.hk.bank.locator.feature.bank.data.remote.api

import ch.app.hk.bank.locator.feature.bank.data.remote.response.Branch
import ch.app.hk.bank.locator.feature.bank.data.remote.response.Response

internal interface BankApi {
    suspend fun getBankBranches(
        type: String,
        lang: String,
        pageSize: Int,
        offset: Int,
    ): Response<Branch>
}
