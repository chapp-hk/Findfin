package ch.app.hk.bank.locator.feature.locator.data.local.datasource

import ch.app.hk.bank.locator.feature.locator.data.local.model.LocatorLocal

interface LocatorLocalDataSource {
    suspend fun insertAll(locators: List<LocatorLocal>)
}
