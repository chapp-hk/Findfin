package ch.app.hk.bank.locator.feature.locator.data.remote.api

import ch.app.hk.bank.locator.feature.locator.data.remote.response.Bank
import ch.app.hk.bank.locator.feature.locator.data.remote.response.Response
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.resources.get
import io.ktor.resources.Resource
import kotlinx.serialization.Serializable

internal class LocatorApiImpl
    constructor(
        private val httpClient: HttpClient,
    ) : LocatorApi {
        override suspend fun getBanks(
            type: String,
            lang: String,
            pageSize: Int,
            offset: Int,
        ): Response<Bank> {
            return httpClient.get(
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
