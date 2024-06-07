package ch.app.hk.bank.locator.feature.locator.data.repo.repository

import ch.app.hk.bank.locator.feature.locator.data.repo.mapper.LocatorFetchResult
import ch.app.hk.bank.locator.feature.locator.data.repo.model.LocatorModel
import ch.app.hk.bank.locator.feature.locator.data.repo.model.LocatorType

interface LocatorRepository {
    suspend fun fetchLocators(
        type: LocatorType,
        localeTag: String,
        page: Int,
        pageSize: Int,
    ): LocatorFetchResult

    suspend fun getLocatorsWithinBound(
        minLat: Double,
        maxLat: Double,
        minLon: Double,
        maxLon: Double,
    ): List<LocatorModel>
}
