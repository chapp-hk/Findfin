package ch.app.hk.bank.locator.feature.locator.data.local.database.dao

import ch.app.framework.hiltext.annotation.HiltExtBindModule
import ch.app.hk.bank.locator.feature.locator.data.local.dao.LocatorDao
import ch.app.hk.bank.locator.feature.locator.data.local.database.entity.LocatorMapper
import ch.app.hk.bank.locator.feature.locator.data.local.entity.LocatorLocal
import org.mapstruct.factory.Mappers
import javax.inject.Inject

@HiltExtBindModule
class LocatorLocalDaoImpl
    @Inject
    constructor(
        private val locatorRoomDao: LocatorRoomDao,
    ) : LocatorDao {
        override suspend fun insertAll(locators: List<LocatorLocal>) {
            val locatorMapper = Mappers.getMapper(LocatorMapper::class.java)
            locatorRoomDao.insertAll(locators.map(locatorMapper::clone))
        }
    }
