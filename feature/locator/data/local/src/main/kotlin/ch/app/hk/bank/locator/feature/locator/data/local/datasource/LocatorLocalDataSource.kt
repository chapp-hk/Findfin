package ch.app.hk.bank.locator.feature.locator.data.local.datasource

import ch.app.hk.bank.locator.feature.locator.data.local.entity.BankLocal

interface LocatorLocalDataSource {
    suspend fun insertAll(banks: List<BankLocal>)
}
