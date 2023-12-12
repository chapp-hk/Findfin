package ch.app.hk.bank.locator.feature.locator.data.remote.datasource

import ch.app.hk.bank.locator.feature.locator.data.remote.api.LocatorApi
import ch.app.hk.bank.locator.feature.locator.data.remote.response.Bank
import ch.app.hk.bank.locator.feature.locator.data.remote.response.LocatorApiError
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext

internal class LocatorRemoteDataSourceImpl
    constructor(
        private val ioDispatcher: CoroutineDispatcher,
        private val locatorApi: LocatorApi,
    ) : LocatorRemoteDataSource {
        override suspend fun getBanks(
            type: String,
            language: String,
            pageSize: Int,
            offset: Int,
        ): List<Bank> {
            return withContext(ioDispatcher) {
                val response =
                    locatorApi.getBanks(
                        type = type,
                        lang = language,
                        pageSize = pageSize,
                        offset = offset,
                    )

                if (response.header == null) {
                    throw LocatorApiError(
                        errorCode = "",
                        errorMessage = "",
                    )
                }

                if (response.header.success.not()) {
                    throw LocatorApiError(
                        errorCode = response.header.errorCode,
                        errorMessage = response.header.errorMessage,
                    )
                }

                response.result!!.records
            }
        }
    }
