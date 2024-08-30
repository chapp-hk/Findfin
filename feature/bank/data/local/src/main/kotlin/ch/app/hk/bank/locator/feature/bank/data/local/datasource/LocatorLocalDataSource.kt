package ch.app.hk.bank.locator.feature.bank.data.local.datasource

import ch.app.hk.bank.locator.feature.bank.data.local.model.LocatorLocal

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
