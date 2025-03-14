package org.chapp.findfin.feature.bank.data.repo.location.local.datasource

import org.chapp.findfin.feature.bank.data.repo.location.local.model.BankLocationLocal

interface BankLocationLocalDataSource {
    suspend fun insertAll(locators: List<BankLocationLocal>)

    suspend fun getBanksWithinBound(
        language: String,
        minLat: Double,
        maxLat: Double,
        minLon: Double,
        maxLon: Double,
    ): List<BankLocationLocal>

    suspend fun getAllBanks(): List<String>

    suspend fun getAll(): List<BankLocationLocal>
}
