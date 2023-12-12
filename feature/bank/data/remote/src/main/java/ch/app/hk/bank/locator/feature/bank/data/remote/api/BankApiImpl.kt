package ch.app.hk.bank.locator.feature.bank.data.remote.api

import ch.app.hk.bank.locator.feature.bank.data.remote.response.Branch
import ch.app.hk.bank.locator.feature.bank.data.remote.response.Response
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.resources.get
import io.ktor.resources.Resource
import kotlinx.serialization.Serializable

internal class BankApiImpl
    constructor(
        private val httpClient: HttpClient,
    ) : BankApi {
        override suspend fun getBankBranches(
            type: String,
            lang: String,
            pageSize: Int,
            offset: Int,
        ): Response<Branch> {
            return httpClient.get(
                BankResource.Branch(
                    type = type,
                    lang = lang,
                    pagesize = pageSize,
                    offset = offset,
                ),
            ).body()
        }

        @Serializable
        @Resource("/public/bank-svf-info")
        internal class BankResource {
            @Resource("/{type}")
            class Branch(
                val parent: BankResource = BankResource(),
                val type: String,
                val lang: String,
                val pagesize: Int,
                val offset: Int,
            )
        }
    }
