package ch.app.hk.bank.locator.feature.locator.data.local.database.dao

import ch.app.framework.hiltext.annotation.HiltExtBindModule
import ch.app.hk.bank.locator.feature.locator.data.local.dao.LocatorDao
import ch.app.hk.bank.locator.feature.locator.data.local.database.entity.BankMapper
import ch.app.hk.bank.locator.feature.locator.data.local.entity.BankLocal
import javax.inject.Inject

@HiltExtBindModule
class LocatorLocalDaoImpl
    @Inject
    constructor(
        private val locatorRoomDao: LocatorRoomDao,
        private val bankMapper: BankMapper,
    ) : LocatorDao {
        override suspend fun insertAll(banks: List<BankLocal>) {
            locatorRoomDao.insertAll(banks.map(bankMapper::clone))
        }
    }
