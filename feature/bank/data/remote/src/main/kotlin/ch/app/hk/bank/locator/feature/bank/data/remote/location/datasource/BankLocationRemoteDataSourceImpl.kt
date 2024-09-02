package ch.app.hk.bank.locator.feature.bank.data.remote.location.datasource

import ch.app.hk.bank.locator.core.logging.appLogger
import ch.app.hk.bank.locator.core.threading.DispatcherIo
import ch.app.hk.bank.locator.feature.bank.data.remote.location.api.BankLocationApi
import ch.app.hk.bank.locator.feature.bank.data.remote.location.api.LocationPath
import ch.app.hk.bank.locator.feature.bank.data.remote.location.model.LocationResult
import ch.app.hk.bank.locator.feature.bank.data.remote.location.model.LocatorApiError
import ch.app.library.hiltwrap.annotation.HiltWrapBindModule
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
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
