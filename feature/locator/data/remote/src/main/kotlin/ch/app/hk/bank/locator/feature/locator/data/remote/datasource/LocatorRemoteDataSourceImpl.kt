package ch.app.hk.bank.locator.feature.locator.data.remote.datasource

import ch.app.hk.bank.locator.core.threading.DispatcherIo
import ch.app.hk.bank.locator.feature.locator.data.remote.api.LocatorApi
import ch.app.hk.bank.locator.feature.locator.data.remote.response.Bank
import ch.app.hk.bank.locator.feature.locator.data.remote.response.LocatorApiError
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

internal class LocatorRemoteDataSourceImpl
    @Inject
    constructor(
        @DispatcherIo private val ioDispatcher: CoroutineDispatcher,
        private val locatorApi: LocatorApi,
    ) : LocatorRemoteDataSource {
        override suspend fun getBanks(
            type: LocatorRemoteDataSource.Type,
            language: String,
            pageSize: Int,
            offset: Int,
        ): List<Bank> {
            return withContext(ioDispatcher) {
                val response =
                    locatorApi.getBanks(
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

@Module
@InstallIn(SingletonComponent::class)
internal interface LocatorRemoteDataSourceModule {
    @Binds
    fun bindLocatorRemoteDataSource(impl: LocatorRemoteDataSourceImpl): LocatorRemoteDataSource
}
