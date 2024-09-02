package ch.app.hk.bank.locator.feature.bank.data.remote.location.api

import ch.app.hk.bank.locator.core.network.HttpClientFactory
import ch.app.hk.bank.locator.feature.bank.data.remote.location.model.BankLocationResponse
import ch.app.hk.bank.locator.feature.bank.data.remote.location.model.Response
import ch.app.library.hiltwrap.annotation.HiltWrapBindModule
import io.ktor.client.call.body
import io.ktor.client.plugins.resources.get
import io.ktor.resources.Resource
import kotlinx.serialization.Serializable
import javax.inject.Inject

@HiltWrapBindModule
internal class BankLocationApiImpl @Inject constructor(
    private val httpClientFactory: HttpClientFactory,
) : BankLocationApi {
    override suspend fun getLocations(
        path: String,
        lang: String,
        pageSize: Int,
        offset: Int,
    ): Response<BankLocationResponse> {
        return httpClientFactory
            .create("https://api.hkma.gov.hk")
            .provide()
            .get(
                BankLocationResource.Type(
                    path = path,
                    lang = lang,
                    pagesize = pageSize,
                    offset = offset,
                ),
            ).body()
    }

    @Serializable
    @Resource("/public/bank-svf-info")
    internal class BankLocationResource {
        @Resource("/{path}")
        class Type(
            val parent: BankLocationResource = BankLocationResource(),
            val path: String,
            val lang: String,
            val pagesize: Int,
            val offset: Int,
        )
    }
}
