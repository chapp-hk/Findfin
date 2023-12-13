package ch.app.hk.bank.locator.feature.locator.data.remote.api

import ch.app.hk.bank.locator.core.network.HttpClientFactory
import ch.app.hk.bank.locator.feature.locator.data.remote.response.Bank
import ch.app.hk.bank.locator.feature.locator.data.remote.response.Response
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.ktor.client.call.body
import io.ktor.client.plugins.resources.get
import io.ktor.resources.Resource
import kotlinx.serialization.Serializable
import javax.inject.Inject

internal class LocatorApiImpl
    @Inject
    constructor(
        private val httpClientFactory: HttpClientFactory,
    ) : LocatorApi {
        override suspend fun getBanks(
            type: String,
            lang: String,
            pageSize: Int,
            offset: Int,
        ): Response<Bank> {
            return httpClientFactory
                .create("https://api.hkma.gov.hk")
                .provide()
                .get(
                    BankInfoResource.Type(
                        type = type,
                        lang = lang,
                        pagesize = pageSize,
                        offset = offset,
                    ),
                ).body()
        }

        @Serializable
        @Resource("/public/bank-svf-info")
        internal class BankInfoResource {
            @Resource("/{type}")
            class Type(
                val parent: BankInfoResource = BankInfoResource(),
                val type: String,
                val lang: String,
                val pagesize: Int,
                val offset: Int,
            )
        }
    }

@Module
@InstallIn(SingletonComponent::class)
internal interface LocatorApiModule {
    @Binds
    fun bindLocatorApi(impl: LocatorApiImpl): LocatorApi
}
