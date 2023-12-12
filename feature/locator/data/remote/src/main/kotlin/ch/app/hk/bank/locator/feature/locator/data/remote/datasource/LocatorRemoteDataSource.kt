package ch.app.hk.bank.locator.feature.locator.data.remote.datasource

import ch.app.hk.bank.locator.feature.locator.data.remote.api.PATH_ATM
import ch.app.hk.bank.locator.feature.locator.data.remote.api.PATH_BRANCH
import ch.app.hk.bank.locator.feature.locator.data.remote.response.Bank

interface LocatorRemoteDataSource {
    enum class Type(val value: String) {
        ATM(PATH_ATM),
        BRANCH(PATH_BRANCH),
    }

    suspend fun getBanks(
        type: Type,
        language: String,
        pageSize: Int,
        offset: Int,
    ): List<Bank>
}
