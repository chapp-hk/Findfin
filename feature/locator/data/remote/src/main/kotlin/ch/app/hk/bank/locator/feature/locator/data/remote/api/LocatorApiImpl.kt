package ch.app.hk.bank.locator.feature.locator.data.remote.api

import ch.app.framework.hiltext.annotation.HiltExtBindModule
import ch.app.hk.bank.locator.core.network.HttpClientFactory
import ch.app.hk.bank.locator.feature.locator.data.remote.response.LocatorResponse
import ch.app.hk.bank.locator.feature.locator.data.remote.response.Response
import io.ktor.client.call.body
import io.ktor.client.plugins.resources.get
import io.ktor.resources.Resource
import kotlinx.serialization.Serializable
import javax.inject.Inject

@HiltExtBindModule
internal class LocatorApiImpl
    @Inject
    constructor(
        private val httpClientFactory: HttpClientFactory,
    ) : LocatorApi {
        override suspend fun getLocators(
            type: String,
            lang: String,
            pageSize: Int,
            offset: Int,
        ): Response<LocatorResponse> {
            return httpClientFactory
                .create("https://api.hkma.gov.hk")
                .provide()
                .get(
                    LocatorResource.Type(
                        type = type,
                        lang = lang,
                        pagesize = pageSize,
                        offset = offset,
                    ),
                ).body()
        }

        @Serializable
        @Resource("/public/bank-svf-info")
        internal class LocatorResource {
            @Resource("/{type}")
            class Type(
                val parent: LocatorResource = LocatorResource(),
                val type: String,
                val lang: String,
                val pagesize: Int,
                val offset: Int,
            )
        }
    }
