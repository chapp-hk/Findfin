package ch.app.hk.bank.locator.feature.bank.data.local.bank.datasource

import ch.app.hk.bank.locator.feature.bank.data.local.bank.model.BankLocationLocal

interface BankLocationLocalDataSource {
    suspend fun insertAll(locators: List<BankLocationLocal>)

    suspend fun getBanksWithinBound(
        minLat: Double,
        maxLat: Double,
        minLon: Double,
        maxLon: Double,
    ): List<BankLocationLocal>

    suspend fun getAllBanks(): List<String>
}
