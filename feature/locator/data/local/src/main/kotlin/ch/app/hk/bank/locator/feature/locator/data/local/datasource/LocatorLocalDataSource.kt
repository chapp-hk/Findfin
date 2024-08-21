package ch.app.hk.bank.locator.feature.locator.data.local.datasource

import ch.app.hk.bank.locator.feature.locator.data.local.model.LocatorLocal

interface LocatorLocalDataSource {
    suspend fun insertAll(locators: List<LocatorLocal>)

    suspend fun getLocatorsWithinBound(
        minLat: Double,
        maxLat: Double,
        minLon: Double,
        maxLon: Double,
    ): List<LocatorLocal>

    suspend fun getAllBanks(): List<String>
}
