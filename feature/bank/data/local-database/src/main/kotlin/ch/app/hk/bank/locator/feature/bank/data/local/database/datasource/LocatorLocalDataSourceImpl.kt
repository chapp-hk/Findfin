package ch.app.hk.bank.locator.feature.bank.data.local.database.datasource

import ch.app.hk.bank.locator.core.logging.appLogger
import ch.app.hk.bank.locator.core.threading.DispatcherIo
import ch.app.hk.bank.locator.feature.bank.data.local.database.model.LocatorMapper
import ch.app.hk.bank.locator.feature.bank.data.local.database.room.LocatorDao
import ch.app.hk.bank.locator.feature.bank.data.local.datasource.LocatorLocalDataSource
import ch.app.hk.bank.locator.feature.bank.data.local.model.LocatorLocal
import ch.app.library.hiltwrap.annotation.HiltWrapBindModule
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import org.mapstruct.factory.Mappers
import javax.inject.Inject

@HiltWrapBindModule
class LocatorLocalDataSourceImpl @Inject constructor(
    @DispatcherIo private val ioDispatcher: CoroutineDispatcher,
    private val locatorDao: LocatorDao,
) : LocatorLocalDataSource {
    private val locatorMapper = Mappers.getMapper(LocatorMapper::class.java)

    override suspend fun insertAll(locators: List<LocatorLocal>) {
        withContext(ioDispatcher) {
            runCatching {
                locatorDao.insertAll(locators.map(locatorMapper::clone))
            }.onFailure { error ->
                appLogger.debug(
                    tag = javaClass.simpleName,
                    message = "insertAll() failed",
                    throwable = error,
                )
            }
        }
    }

    override suspend fun getLocatorsWithinBound(
        minLat: Double,
        maxLat: Double,
        minLon: Double,
        maxLon: Double,
    ): List<LocatorLocal> {
        return withContext(ioDispatcher) {
            runCatching {
                locatorDao.getLocatorsWithinBound(
                    minLat = minLat,
                    maxLat = maxLat,
                    minLon = minLon,
                    maxLon = maxLon,
                ).map(locatorMapper::toLocalModel)
            }.getOrElse { error ->
                appLogger.debug(
                    tag = javaClass.simpleName,
                    message = "getLocatorsWithinBound() failed",
                    throwable = error,
                )
                emptyList()
            }
        }
    }

    override suspend fun getAllBanks(): List<String> {
        return withContext(ioDispatcher) {
            runCatching {
                locatorDao.getDistinctBanks()
            }.getOrElse { error ->
                appLogger.debug(
                    tag = javaClass.simpleName,
                    message = "getLocatorsWithinBound() failed",
                    throwable = error,
                )
                emptyList()
            }
        }
    }
}
