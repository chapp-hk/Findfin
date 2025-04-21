package org.chapp.findfin.feature.bank.data.local.database.datasource

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import org.chapp.findfin.core.logging.appLogger
import org.chapp.findfin.core.threading.DispatcherIo
import org.chapp.findfin.feature.bank.data.local.database.dao.BankDao
import org.chapp.findfin.feature.bank.data.local.database.mapper.BankLocalMapper
import org.chapp.findfin.feature.bank.data.repo.datasource.local.datasource.BankLocalDataSource
import org.chapp.findfin.feature.bank.data.repo.datasource.local.model.BankLocal
import org.chapp.library.hiltwrap.annotation.HiltWrapBindModule
import org.mapstruct.factory.Mappers
import javax.inject.Inject

@HiltWrapBindModule
internal class BankLocalDataSourceImpl @Inject constructor(
    @DispatcherIo private val ioDispatcher: CoroutineDispatcher,
    private val bankDao: BankDao,
) : BankLocalDataSource {
    private val bankLocalMapper = Mappers.getMapper(BankLocalMapper::class.java)

    override suspend fun insertAll(locators: List<BankLocal>) {
        withContext(ioDispatcher) {
            runCatching {
                bankDao.insertAll(locators.map(bankLocalMapper::toDatabaseEntity))
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
        language: String,
        minLat: Double,
        maxLat: Double,
        minLon: Double,
        maxLon: Double,
    ): List<BankLocal> {
        return withContext(ioDispatcher) {
            runCatching {
                bankDao.getBanksWithinBound(
                    language = language,
                    minLat = minLat,
                    maxLat = maxLat,
                    minLon = minLon,
                    maxLon = maxLon,
                ).map(bankLocalMapper::toLocalModel)
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
                bankDao.getDistinctBanks()
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

    override suspend fun getAll(): List<BankLocal> {
        return withContext(ioDispatcher) {
            runCatching {
                bankDao
                    .getAll()
                    .map(bankLocalMapper::toLocalModel)
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
