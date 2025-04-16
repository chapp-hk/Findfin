package org.chapp.findfin.feature.bank.data.repo.local.datasource

import org.chapp.findfin.feature.bank.data.repo.local.model.BankLocal

interface BankLocalDataSource {
    suspend fun insertAll(locators: List<BankLocal>)

    suspend fun getBanksWithinBound(
        language: String,
        minLat: Double,
        maxLat: Double,
        minLon: Double,
        maxLon: Double,
    ): List<BankLocal>

    suspend fun getAllBanks(): List<String>

    suspend fun getAll(): List<BankLocal>
}
