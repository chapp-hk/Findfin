package org.chapp.findfin.feature.bank.data.remote.network.api

import io.ktor.client.call.body
import io.ktor.client.plugins.resources.get
import io.ktor.resources.Resource
import kotlinx.serialization.Serializable
import org.chapp.findfin.core.network.HttpClientFactory
import org.chapp.findfin.feature.bank.data.remote.network.model.BankLocationResponse
import org.chapp.findfin.feature.bank.data.remote.network.model.Response
import org.chapp.library.hiltwrap.annotation.HiltWrapBindModule
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
