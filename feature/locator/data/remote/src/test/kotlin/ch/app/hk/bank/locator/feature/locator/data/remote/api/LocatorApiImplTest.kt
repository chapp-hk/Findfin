package ch.app.hk.bank.locator.feature.locator.data.remote.api

import ch.app.hk.bank.locator.core.network.HttpClientFactory
import ch.app.hk.bank.locator.core.network.createKtor
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
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test

@DisplayName("LocatorApiImplTest unit tests")
class LocatorApiImplTest {
    private val mockBaseUrl = "http://localhost"

    private lateinit var mockEngine: MockEngine
    private lateinit var bankApi: LocatorApiImpl
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

        bankApi = LocatorApiImpl(httpClientFactory = httpClientFactory)
    }

    @AfterEach
    fun tearDown() {
        mockEngine.close()
    }

    @Test
    @DisplayName("When invoke getLocators(), should request correct url with get method")
    fun `test getLocators`() =
        runTest(StandardTestDispatcher()) {
            shouldThrowAny {
                bankApi.getLocators(
                    type = "banks-branch-locator",
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
