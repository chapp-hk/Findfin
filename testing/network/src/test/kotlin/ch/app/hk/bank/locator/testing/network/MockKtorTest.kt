package ch.app.hk.bank.locator.testing.network

import io.kotest.matchers.nulls.shouldNotBeNull
import io.ktor.client.request.get
import io.ktor.http.HttpHeaders
import io.ktor.http.HttpStatusCode
import io.ktor.http.headersOf
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test

@DisplayName("MockKtor unit tests")
class MockKtorTest {
    @Test
    fun `test lastRequest()`() =
        runTest(StandardTestDispatcher()) {
            val mockKtor =
                MockKtor(
                    baseUrl = "http://localhost",
                    contentFilePath = "path/to/file",
                    status = HttpStatusCode.NotFound,
                    headers = headersOf(HttpHeaders.ContentType, "application/xml"),
                )

            mockKtor.mockHttpClient.get(urlString = "http://localhost")

            mockKtor.lastRequest().shouldNotBeNull()
        }
}
