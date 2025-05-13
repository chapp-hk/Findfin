package org.chapp.findfin.feature.bank.data.repo.datasource.local.datasource

import org.chapp.findfin.feature.bank.data.repo.datasource.local.model.BankLocal
import org.chapp.findfin.feature.bank.data.repo.datasource.local.model.BankQueryParameters

interface BankLocalDataSource {
    suspend fun insertAll(locators: List<BankLocal>)

    suspend fun getAllBanks(language: String): List<String>

    suspend fun getBanksWithParameters(params: BankQueryParameters): List<BankLocal>
}
