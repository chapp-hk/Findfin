package ch.app.hk.bank.locator.feature.locator.data.remote.datasource

import ch.app.framework.hiltext.annotation.HiltExtBindModule
import ch.app.hk.bank.locator.core.threading.DispatcherIo
import ch.app.hk.bank.locator.feature.locator.data.remote.api.LocatorApi
import ch.app.hk.bank.locator.feature.locator.data.remote.api.LocatorPath
import ch.app.hk.bank.locator.feature.locator.data.remote.response.LocatorApiError
import ch.app.hk.bank.locator.feature.locator.data.remote.response.LocatorResponse
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
    ): Result<List<LocatorResponse>> {
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

                response.result!!.records
            }
        }
    }
}
