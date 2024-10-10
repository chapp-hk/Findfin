package org.chapp.findfin.feature.bank.data.local.database.location.datasource

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import org.chapp.findfin.core.logging.appLogger
import org.chapp.findfin.core.threading.DispatcherIo
import org.chapp.findfin.feature.bank.data.local.database.location.dao.BankLocationDao
import org.chapp.findfin.feature.bank.data.local.database.location.model.BankLocationMapper
import org.chapp.findfin.feature.bank.data.repo.location.local.datasource.BankLocationLocalDataSource
import org.chapp.findfin.feature.bank.data.repo.location.local.model.BankLocationLocal
import org.chapp.library.hiltwrap.annotation.HiltWrapBindModule
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

    override suspend fun getAll(): List<BankLocationLocal> {
        return withContext(ioDispatcher) {
            runCatching {
                bankLocationDao
                    .getAll()
                    .map(bankLocationMapper::toLocalModel)
            }.getOrElse { error ->
                appLogger.debug(
                    tag = javaClass.simpleName,
                    message = "getAll() failed",
                    throwable = error,
                )
                emptyList()
            }
        }
    }
}
