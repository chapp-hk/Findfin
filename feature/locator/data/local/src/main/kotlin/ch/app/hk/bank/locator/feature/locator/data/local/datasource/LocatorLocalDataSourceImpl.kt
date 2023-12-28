package ch.app.hk.bank.locator.feature.locator.data.local.datasource

import ch.app.framework.hiltext.annotation.HiltExtBindModule
import ch.app.hk.bank.locator.core.threading.DispatcherIo
import ch.app.hk.bank.locator.feature.locator.data.local.dao.LocatorDao
import ch.app.hk.bank.locator.feature.locator.data.local.entity.BankLocal
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltExtBindModule
class LocatorLocalDataSourceImpl
    @Inject
    constructor(
        @DispatcherIo private val ioDispatcher: CoroutineDispatcher,
        private val locatorDao: LocatorDao,
    ) : LocatorLocalDataSource {
        override suspend fun insertAll(banks: List<BankLocal>) {
            withContext(ioDispatcher) {
                locatorDao.insertAll(banks)
            }
        }
    }
