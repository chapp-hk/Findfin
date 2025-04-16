package org.chapp.findfin.feature.bank.data.remote.network.datasource

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import org.chapp.findfin.core.logging.appLogger
import org.chapp.findfin.core.threading.DispatcherIo
import org.chapp.findfin.feature.bank.data.remote.network.api.BankLocationApi
import org.chapp.findfin.feature.bank.data.remote.network.api.LocationPath
import org.chapp.findfin.feature.bank.data.remote.network.model.LocationResult
import org.chapp.findfin.feature.bank.data.remote.network.model.LocatorApiError
import org.chapp.library.hiltwrap.annotation.HiltWrapBindModule
import javax.inject.Inject

@HiltWrapBindModule
internal class BankLocationRemoteDataSourceImpl @Inject constructor(
    @DispatcherIo private val ioDispatcher: CoroutineDispatcher,
    private val bankLocationApi: BankLocationApi,
) : BankLocationRemoteDataSource {
    override suspend fun getLocations(
        path: LocationPath,
        language: String,
        pageSize: Int,
        offset: Int,
    ): LocationResult {
        return withContext(ioDispatcher) {
            runCatching {
                val response =
                    bankLocationApi.getLocations(
                        path = path.value,
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

                LocationResult.Success(response.result!!.records)
            }.getOrElse { error ->
                appLogger.debug(
                    tag = javaClass.simpleName,
                    message = "getLocators() failed",
                    throwable = error,
                )
                LocationResult.Error
            }
        }
    }
}
