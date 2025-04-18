package org.chapp.findfin.feature.bank.data.remote.network.datasource

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import org.chapp.findfin.core.logging.appLogger
import org.chapp.findfin.core.threading.DispatcherIo
import org.chapp.findfin.feature.bank.data.remote.network.api.BankApi
import org.chapp.findfin.feature.bank.data.remote.network.api.TypePath
import org.chapp.findfin.feature.bank.data.remote.network.model.BankApiError
import org.chapp.findfin.feature.bank.data.remote.network.model.BankResult
import org.chapp.library.hiltwrap.annotation.HiltWrapBindModule
import javax.inject.Inject

@HiltWrapBindModule
internal class BankRemoteDataSourceImpl @Inject constructor(
    @DispatcherIo private val ioDispatcher: CoroutineDispatcher,
    private val bankApi: BankApi,
) : BankRemoteDataSource {
    override suspend fun getLocations(
        path: TypePath,
        language: String,
        pageSize: Int,
        offset: Int,
    ): BankResult {
        return withContext(ioDispatcher) {
            runCatching {
                val response =
                    bankApi.getLocations(
                        path = path.value,
                        lang = language,
                        pageSize = pageSize,
                        offset = offset,
                    )

                if (response.header == null) {
                    throw BankApiError(
                        errorCode = "",
                        errorMessage = "",
                    )
                }

                if (response.header.success.not()) {
                    throw BankApiError(
                        errorCode = response.header.errorCode,
                        errorMessage = response.header.errorMessage,
                    )
                }

                BankResult.Success(response.result!!.records)
            }.getOrElse { error ->
                appLogger.debug(
                    tag = javaClass.simpleName,
                    message = "getLocators() failed",
                    throwable = error,
                )
                BankResult.Error
            }
        }
    }
}
