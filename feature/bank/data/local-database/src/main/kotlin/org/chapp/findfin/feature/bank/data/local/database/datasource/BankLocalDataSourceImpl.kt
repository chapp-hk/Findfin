package org.chapp.findfin.feature.bank.data.local.database.datasource

import androidx.sqlite.db.SimpleSQLiteQuery
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

    override suspend fun getAllBanks(language: String): List<String> {
        return withContext(ioDispatcher) {
            runCatching {
                bankDao.getDistinctBankNames(language = language)
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

    override suspend fun getBanksWithParameters(
        language: String,
        bankName: String?,
        type: String?,
        minLat: Double?,
        maxLat: Double?,
        minLon: Double?,
        maxLon: Double?,
    ): List<BankLocal> {
        return withContext(ioDispatcher) {
            /**
             * The WHERE 1=1 in the query serves as a placeholder to simplify the dynamic
             * construction of SQL queries. It ensures that all subsequent conditions can be
             * appended with AND without needing to check if it's the first condition.
             * This avoids extra logic to handle the absence of an initial WHERE clause or
             * to determine whether to use AND or WHERE for the first condition.
             */
            val queryBuilder = StringBuilder("SELECT * FROM bank WHERE 1=1")
            val args = mutableListOf<Any>()

            queryBuilder.append(" AND language = ?")
            args.add(language)

            type?.let {
                queryBuilder.append(" AND type = ?")
                args.add(it)
            }
            bankName?.let {
                queryBuilder.append(" AND bank_name = ?")
                args.add(it)
            }
            if (minLat != null && maxLat != null) {
                queryBuilder.append(" AND latitude BETWEEN ? AND ?")
                args.add(minLat)
                args.add(maxLat)
            }

            if (minLon != null && maxLon != null) {
                queryBuilder.append(" AND longitude BETWEEN ? AND ?")
                args.add(minLon)
                args.add(maxLon)
            }

            val query = SimpleSQLiteQuery(queryBuilder.toString(), args.toTypedArray())
            bankDao.getBanksWithQuery(query).map(bankLocalMapper::toLocalModel)
        }
    }
}
