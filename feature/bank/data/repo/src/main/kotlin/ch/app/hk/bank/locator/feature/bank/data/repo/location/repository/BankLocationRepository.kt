package ch.app.hk.bank.locator.feature.bank.data.repo.location.repository

import ch.app.hk.bank.locator.feature.bank.data.repo.location.mapper.BankLocationFetchResult
import ch.app.hk.bank.locator.feature.bank.data.repo.location.model.BankLocationBound
import ch.app.hk.bank.locator.feature.bank.data.repo.location.model.BankLocationModel
import ch.app.hk.bank.locator.feature.bank.data.repo.location.model.BankLocationType

interface BankLocationRepository {
    suspend fun fetchLocators(
        type: BankLocationType,
        localeTag: String,
        page: Int,
        pageSize: Int,
    ): BankLocationFetchResult

    suspend fun getLocatorsWithinBound(bound: BankLocationBound): List<BankLocationModel>

    suspend fun getAllBanks(): List<String>
}
