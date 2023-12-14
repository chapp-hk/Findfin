package ch.app.hk.bank.locator.feature.locator.data.local.dao

import ch.app.hk.bank.locator.feature.locator.data.local.entity.BankLocal

interface LocatorDao {
    suspend fun insertAll(banks: List<BankLocal>)
}
