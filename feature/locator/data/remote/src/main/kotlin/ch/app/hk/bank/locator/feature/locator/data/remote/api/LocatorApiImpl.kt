package ch.app.hk.bank.locator.feature.locator.data.remote.api

import ch.app.hk.bank.locator.core.network.HttpClientFactory
import ch.app.hk.bank.locator.feature.locator.data.remote.model.LocatorResponse
import ch.app.hk.bank.locator.feature.locator.data.remote.model.Response
import ch.app.library.hiltwrap.annotation.HiltWrapBindModule
import io.ktor.client.call.body
import io.ktor.client.plugins.resources.get
import io.ktor.resources.Resource
import kotlinx.serialization.Serializable
import javax.inject.Inject

@HiltWrapBindModule
internal class LocatorApiImpl @Inject constructor(
    private val httpClientFactory: HttpClientFactory,
) : LocatorApi {
    override suspend fun getLocators(
        path: String,
        lang: String,
        pageSize: Int,
        offset: Int,
    ): Response<LocatorResponse> {
        return httpClientFactory
            .create("https://api.hkma.gov.hk")
            .provide()
            .get(
                LocatorResource.Type(
                    path = path,
                    lang = lang,
                    pagesize = pageSize,
                    offset = offset,
                ),
            ).body()
    }

    @Serializable
    @Resource("/public/bank-svf-info")
    internal class LocatorResource {
        @Resource("/{path}")
        class Type(
            val parent: LocatorResource = LocatorResource(),
            val path: String,
            val lang: String,
            val pagesize: Int,
            val offset: Int,
        )
    }
}
