package ch.app.hk.bank.locator.feature.bank.data.remote.api

import ch.app.hk.bank.locator.core.network.createKtor
import io.kotest.assertions.throwables.shouldThrowAny
import io.kotest.matchers.shouldBe
import io.ktor.client.HttpClient
import io.ktor.client.engine.mock.MockEngine
import io.ktor.client.engine.mock.respond
import io.ktor.client.plugins.logging.EMPTY
import io.ktor.client.plugins.logging.Logger
import io.ktor.http.HttpHeaders
import io.ktor.http.HttpMethod
import io.ktor.http.HttpStatusCode
import io.ktor.http.headersOf
import io.ktor.utils.io.ByteReadChannel
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test

@DisplayName("BankApiImpl unit tests")
class BankApiImplTest {
    private val mockBaseUrl = "http://localhost"

    private lateinit var mockEngine: MockEngine
    private lateinit var httpClient: HttpClient
    private lateinit var bankApi: BankApiImpl

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

        httpClient =
            createKtor(
                httpClientEngine = mockEngine,
                loggingHandler = Logger.EMPTY,
                baseUrl = mockBaseUrl,
            )

        bankApi = BankApiImpl(httpClient = httpClient)
    }

    @AfterEach
    fun tearDown() {
        mockEngine.close()
    }

    @Test
    @DisplayName("When invoke getBankBranches(), should request correct url with get method")
    fun `test getBankBranches`() =
        runTest(StandardTestDispatcher()) {
            shouldThrowAny {
                bankApi.getBankBranches(
                    pageSize = 1000,
                    offset = 500,
                )
            }

            mockEngine.requestHistory.first().let {
                it.url.toString() shouldBe "$mockBaseUrl/public/bank-svf-info/banks-branch-locator?pagesize=1000&offset=500"
                it.method shouldBe HttpMethod.Get
            }
        }
}
