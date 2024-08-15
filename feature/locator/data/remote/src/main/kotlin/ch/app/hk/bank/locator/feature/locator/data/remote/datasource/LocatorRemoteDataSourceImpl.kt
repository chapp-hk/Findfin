package ch.app.hk.bank.locator.feature.locator.data.remote.datasource

import ch.app.hk.bank.locator.core.logging.appLogger
import ch.app.hk.bank.locator.core.threading.DispatcherIo
import ch.app.hk.bank.locator.feature.locator.data.remote.api.LocatorApi
import ch.app.hk.bank.locator.feature.locator.data.remote.api.LocatorPath
import ch.app.hk.bank.locator.feature.locator.data.remote.model.LocatorApiError
import ch.app.hk.bank.locator.feature.locator.data.remote.model.LocatorResult
import ch.app.library.hiltwrap.annotation.HiltExtBindModule
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltExtBindModule
internal class LocatorRemoteDataSourceImpl @Inject constructor(
    @DispatcherIo private val ioDispatcher: CoroutineDispatcher,
    private val locatorApi: LocatorApi,
) : LocatorRemoteDataSource {
    override suspend fun getLocators(
        path: LocatorPath,
        language: String,
        pageSize: Int,
        offset: Int,
    ): LocatorResult {
        return withContext(ioDispatcher) {
            runCatching {
                val response =
                    locatorApi.getLocators(
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

                LocatorResult.Success(response.result!!.records)
            }.getOrElse { error ->
                appLogger.debug(
                    tag = javaClass.simpleName,
                    message = "getLocators() failed",
                    throwable = error,
                )
                LocatorResult.Error
            }
        }
    }
}
