package ch.app.hk.bank.locator.feature.bank.data.repo.repository

import ch.app.hk.bank.locator.feature.bank.data.repo.mapper.LocatorFetchResult
import ch.app.hk.bank.locator.feature.bank.data.repo.model.LocationBound
import ch.app.hk.bank.locator.feature.bank.data.repo.model.LocatorModel
import ch.app.hk.bank.locator.feature.bank.data.repo.model.LocatorType

interface LocatorRepository {
    suspend fun fetchLocators(
        type: LocatorType,
        localeTag: String,
        page: Int,
        pageSize: Int,
    ): LocatorFetchResult

    suspend fun getLocatorsWithinBound(bound: LocationBound): List<LocatorModel>

    suspend fun getAllBanks(): List<String>
}
