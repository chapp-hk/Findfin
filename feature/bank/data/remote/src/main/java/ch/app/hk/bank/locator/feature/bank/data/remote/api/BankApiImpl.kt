package ch.app.hk.bank.locator.feature.bank.data.remote.api

import ch.app.hk.bank.locator.feature.bank.data.remote.response.Branch
import ch.app.hk.bank.locator.feature.bank.data.remote.response.Response
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.resources.get
import io.ktor.http.parameters
import io.ktor.resources.Resource
import kotlinx.serialization.Serializable

internal class BankApiImpl
    constructor(
        private val httpClient: HttpClient,
    ) : BankApi {
        override suspend fun getBankBranches(
            pageSize: Int,
            offset: Int,
        ): Response<Branch> {
            return httpClient.get(
                BankResource.Branch(
                    pageSize = pageSize,
                    offset = offset,
                ),
            ) {
                parameters {
                    append("pagesize", pageSize.toString())
                    append("offset", offset.toString())
                }
            }.body()
        }

        @Serializable
        @Resource("/public/bank-svf-info")
        internal class BankResource {
            @Resource("/banks-branch-locator")
            class Branch(
                val parent: BankResource = BankResource(),
                val pageSize: Int,
                val offset: Int,
            )
        }
    }
