package ch.app.hk.bank.locator.feature.locator.data.local.dao

import ch.app.hk.bank.locator.feature.locator.data.local.entity.LocatorLocal

interface LocatorDao {
    suspend fun insertAll(locators: List<LocatorLocal>)
}
