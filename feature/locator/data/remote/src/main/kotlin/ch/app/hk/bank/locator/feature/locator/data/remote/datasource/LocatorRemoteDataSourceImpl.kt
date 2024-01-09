package ch.app.hk.bank.locator.feature.locator.data.remote.datasource

import ch.app.framework.hiltext.annotation.HiltExtBindModule
import ch.app.hk.bank.locator.core.threading.DispatcherIo
import ch.app.hk.bank.locator.feature.locator.data.remote.api.LocatorApi
import ch.app.hk.bank.locator.feature.locator.data.remote.api.LocatorType
import ch.app.hk.bank.locator.feature.locator.data.remote.response.LocatorApiError
import ch.app.hk.bank.locator.feature.locator.data.remote.response.LocatorResponse
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltExtBindModule
internal class LocatorRemoteDataSourceImpl
    @Inject
    constructor(
        @DispatcherIo private val ioDispatcher: CoroutineDispatcher,
        private val locatorApi: LocatorApi,
    ) : LocatorRemoteDataSource {
        override suspend fun getLocators(
            type: LocatorType,
            language: String,
            pageSize: Int,
            offset: Int,
        ): List<LocatorResponse> {
            return withContext(ioDispatcher) {
                val response =
                    locatorApi.getLocators(
                        type = type.value,
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
