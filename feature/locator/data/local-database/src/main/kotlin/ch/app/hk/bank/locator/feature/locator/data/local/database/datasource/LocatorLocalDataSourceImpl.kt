package ch.app.hk.bank.locator.feature.locator.data.local.database.datasource

import ch.app.framework.hiltext.annotation.HiltExtBindModule
import ch.app.hk.bank.locator.core.threading.DispatcherIo
import ch.app.hk.bank.locator.feature.locator.data.local.database.entity.LocatorMapper
import ch.app.hk.bank.locator.feature.locator.data.local.database.room.LocatorDao
import ch.app.hk.bank.locator.feature.locator.data.local.datasource.LocatorLocalDataSource
import ch.app.hk.bank.locator.feature.locator.data.local.entity.LocatorLocal
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import org.mapstruct.factory.Mappers
import javax.inject.Inject

@HiltExtBindModule
class LocatorLocalDataSourceImpl @Inject constructor(
    @DispatcherIo private val ioDispatcher: CoroutineDispatcher,
    private val locatorDao: LocatorDao,
) : LocatorLocalDataSource {
    override suspend fun insertAll(locators: List<LocatorLocal>) {
        withContext(ioDispatcher) {
            val locatorMapper = Mappers.getMapper(LocatorMapper::class.java)
            locatorDao.insertAll(locators.map(locatorMapper::clone))
        }
    }
}
