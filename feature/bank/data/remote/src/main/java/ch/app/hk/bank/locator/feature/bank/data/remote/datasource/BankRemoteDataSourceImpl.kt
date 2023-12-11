package ch.app.hk.bank.locator.feature.bank.data.remote.datasource

import ch.app.hk.bank.locator.feature.bank.data.remote.api.BankApi
import ch.app.hk.bank.locator.feature.bank.data.remote.response.BankApiError
import ch.app.hk.bank.locator.feature.bank.data.remote.response.Branch
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext

internal class BankRemoteDataSourceImpl
    constructor(
        private val ioDispatcher: CoroutineDispatcher,
        private val bankApi: BankApi,
    ) : BankRemoteDataSource {
        override suspend fun getBankBranches(
            language: String,
            pageSize: Int,
            offset: Int,
        ): List<Branch> {
            return withContext(ioDispatcher) {
                val response =
                    bankApi.getBankBranches(
                        lang = language,
                        pageSize = pageSize,
                        offset = offset,
                    )

                if (response.header.success) {
                    response.result?.records.orEmpty()
                } else {
                    throw BankApiError(
                        errorCode = response.header.errorCode,
                        errorMessage = response.header.errorMessage,
                    )
                }
            }
        }
    }
