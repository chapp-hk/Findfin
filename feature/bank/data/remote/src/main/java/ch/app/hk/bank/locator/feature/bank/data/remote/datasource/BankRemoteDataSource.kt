package ch.app.hk.bank.locator.feature.bank.data.remote.datasource

import ch.app.hk.bank.locator.feature.bank.data.remote.response.Branch

interface BankRemoteDataSource {
    suspend fun getBankBranches(
        type: String,
        language: String,
        pageSize: Int,
        offset: Int,
    ): List<Branch>
}
