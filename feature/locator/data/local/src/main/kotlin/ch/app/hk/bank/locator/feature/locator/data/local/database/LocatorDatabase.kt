package ch.app.hk.bank.locator.feature.locator.data.local.database

import ch.app.hk.bank.locator.feature.locator.data.local.entity.BankLocal

internal interface LocatorDatabase {
    suspend fun insertAll(banks: List<BankLocal>)
}
