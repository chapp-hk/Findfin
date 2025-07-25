package org.chapp.findfin.feature.bank.data.remote.network.api

import io.ktor.client.call.body
import io.ktor.client.plugins.resources.get
import io.ktor.resources.Resource
import kotlinx.serialization.Serializable
import org.chapp.findfin.core.network.HttpClientFactory
import org.chapp.findfin.feature.bank.data.remote.network.model.BankResponse
import org.chapp.findfin.feature.bank.data.remote.network.model.Response
import org.chapp.library.hiltwrap.annotation.HiltWrapBindModule
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
@HiltWrapBindModule
internal class BankApiImpl @Inject constructor(
    private val httpClientFactory: HttpClientFactory,
) : BankApi {
    override suspend fun getLocations(
        path: String,
        lang: String,
        pageSize: Int,
        offset: Int,
    ): Response<BankResponse> {
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
