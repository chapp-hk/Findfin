package org.chapp.findfin.feature.bank.data.remote.network.api

import io.kotest.assertions.throwables.shouldThrowAny
import io.kotest.matchers.shouldBe
import io.ktor.client.engine.mock.MockEngine
import io.ktor.client.engine.mock.respond
import io.ktor.client.plugins.logging.EMPTY
import io.ktor.client.plugins.logging.Logger
import io.ktor.http.HttpHeaders
import io.ktor.http.HttpMethod
import io.ktor.http.HttpStatusCode
import io.ktor.http.headersOf
import io.ktor.utils.io.ByteReadChannel
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.runTest
import org.chapp.findfin.core.network.HttpClientFactory
import org.chapp.findfin.core.network.createKtor
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test

@DisplayName("BankLocationApiImpl unit tests")
class BankApiImplTest {
    private val mockBaseUrl = "http://localhost"

    private lateinit var mockEngine: MockEngine
    private lateinit var bankApi: BankApiImpl
    private val httpClientFactory = mockk<HttpClientFactory>()

    @BeforeEach
    fun setUp() {
        mockEngine =
            MockEngine { _ ->
                respond(
                    content = ByteReadChannel(""),
                    status = HttpStatusCode.OK,
                    headers = headersOf(HttpHeaders.ContentType, "application/json"),
                )
            }

        every { httpClientFactory.create(any()).provide() } returns
            createKtor(
                httpClientEngine = mockEngine,
                loggingHandler = Logger.EMPTY,
                baseUrl = mockBaseUrl,
            )

        bankApi = BankApiImpl(httpClientFactory = httpClientFactory)
    }

    @AfterEach
    fun tearDown() {
        mockEngine.close()
    }

    @Test
    @DisplayName("When invoke getLocations(), should request correct url with get method")
    fun `test getLocations`() =
        runTest(StandardTestDispatcher()) {
            shouldThrowAny {
                bankApi.getLocations(
                    path = "banks-branch-locator",
                    lang = "en",
                    pageSize = 1000,
                    offset = 500,
                )
            }

            mockEngine.requestHistory.first().let {
                it.url.toString() shouldBe
                    "$mockBaseUrl/public/bank-svf-info/banks-branch-locator?lang=en&pagesize=1000&offset=500"

                it.method shouldBe
                    HttpMethod.Get
            }
        }
}
