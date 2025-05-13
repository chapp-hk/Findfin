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
import org.chapp.findfin.feature.bank.data.repo.datasource.local.model.BankQueryParameters
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

    override suspend fun getBanksWithParameters(params: BankQueryParameters): List<BankLocal> {
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
            args.add(params.language)

            params.type?.let {
                queryBuilder.append(" AND type = ?")
                args.add(it)
            }
            params.bankName?.let {
                queryBuilder.append(" AND bank_name = ?")
                args.add(it)
            }
            if (params.minLat != null && params.maxLat != null) {
                queryBuilder.append(" AND latitude BETWEEN ? AND ?")
                args.add(params.minLat!!)
                args.add(params.maxLat!!)
            }
            if (params.minLon != null && params.maxLon != null) {
                queryBuilder.append(" AND longitude BETWEEN ? AND ?")
                args.add(params.minLon!!)
                args.add(params.maxLon!!)
            }

            runCatching {
                val query = SimpleSQLiteQuery(queryBuilder.toString(), args.toTypedArray())
                bankDao.getBanksWithQuery(query).map(bankLocalMapper::toLocalModel)
            }.getOrElse { error ->
                appLogger.debug(
                    tag = javaClass.simpleName,
                    message = "getBanksWithParameters() failed",
                    throwable = error,
                )
                emptyList()
            }
        }
    }
}
