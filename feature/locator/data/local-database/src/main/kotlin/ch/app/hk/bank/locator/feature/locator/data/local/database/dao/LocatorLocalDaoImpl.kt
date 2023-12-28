package ch.app.hk.bank.locator.feature.locator.data.local.database.dao

import ch.app.framework.hiltext.annotation.HiltExtBindModule
import ch.app.hk.bank.locator.feature.locator.data.local.dao.LocatorDao
import ch.app.hk.bank.locator.feature.locator.data.local.database.entity.BankMapper
import ch.app.hk.bank.locator.feature.locator.data.local.entity.BankLocal
import org.mapstruct.factory.Mappers
import javax.inject.Inject

@HiltExtBindModule
class LocatorLocalDaoImpl
    @Inject
    constructor(
        private val locatorRoomDao: LocatorRoomDao,
    ) : LocatorDao {
        override suspend fun insertAll(banks: List<BankLocal>) {
            val bankMapper = Mappers.getMapper(BankMapper::class.java)
            locatorRoomDao.insertAll(banks.map(bankMapper::clone))
        }
    }
