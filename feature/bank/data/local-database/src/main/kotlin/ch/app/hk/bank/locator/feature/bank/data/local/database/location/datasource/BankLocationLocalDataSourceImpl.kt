package ch.app.hk.bank.locator.feature.bank.data.local.database.location.datasource

import ch.app.hk.bank.locator.core.logging.appLogger
import ch.app.hk.bank.locator.core.threading.DispatcherIo
import ch.app.hk.bank.locator.feature.bank.data.local.database.location.dao.BankLocationDao
import ch.app.hk.bank.locator.feature.bank.data.local.database.location.model.BankLocationMapper
import ch.app.hk.bank.locator.feature.bank.data.repo.location.local.datasource.BankLocationLocalDataSource
import ch.app.hk.bank.locator.feature.bank.data.repo.location.local.model.BankLocationLocal
import ch.app.library.hiltwrap.annotation.HiltWrapBindModule
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import org.mapstruct.factory.Mappers
import javax.inject.Inject

@HiltWrapBindModule
internal class BankLocationLocalDataSourceImpl @Inject constructor(
    @DispatcherIo private val ioDispatcher: CoroutineDispatcher,
    private val bankLocationDao: BankLocationDao,
) : BankLocationLocalDataSource {
    private val bankLocationMapper = Mappers.getMapper(BankLocationMapper::class.java)

    override suspend fun insertAll(locators: List<BankLocationLocal>) {
        withContext(ioDispatcher) {
            runCatching {
                bankLocationDao.insertAll(locators.map(bankLocationMapper::clone))
            }.onFailure { error ->
                appLogger.debug(
                    tag = javaClass.simpleName,
                    message = "insertAll() failed",
                    throwable = error,
                )
            }
        }
    }

    override suspend fun getBanksWithinBound(
        minLat: Double,
        maxLat: Double,
        minLon: Double,
        maxLon: Double,
    ): List<BankLocationLocal> {
        return withContext(ioDispatcher) {
            runCatching {
                bankLocationDao.getLocatorsWithinBound(
                    minLat = minLat,
                    maxLat = maxLat,
                    minLon = minLon,
                    maxLon = maxLon,
                ).map(bankLocationMapper::toLocalModel)
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
                bankLocationDao.getDistinctBanks()
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
