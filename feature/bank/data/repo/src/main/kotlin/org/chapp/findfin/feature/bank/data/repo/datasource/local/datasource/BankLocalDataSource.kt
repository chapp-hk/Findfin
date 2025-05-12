package org.chapp.findfin.feature.bank.data.repo.datasource.local.datasource

import org.chapp.findfin.feature.bank.data.repo.datasource.local.model.BankLocal

interface BankLocalDataSource {
    suspend fun insertAll(locators: List<BankLocal>)

    suspend fun getAllBanks(language: String): List<String>

    suspend fun getBanksWithParameters(
        language: String,
        bankName: String?,
        type: String?,
        minLat: Double?,
        maxLat: Double?,
        minLon: Double?,
        maxLon: Double?,
    ): List<BankLocal>
}
