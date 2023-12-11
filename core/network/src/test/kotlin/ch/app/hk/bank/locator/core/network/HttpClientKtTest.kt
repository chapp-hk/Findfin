package ch.app.hk.bank.locator.core.network

import io.kotest.matchers.shouldBe
import io.ktor.client.engine.android.Android
import io.ktor.client.plugins.logging.EMPTY
import io.ktor.client.plugins.logging.Logger
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Test

class HttpClientKtTest {
    @Test
    fun `createKtor test`() =
        runTest {
            val androidEngine = Android.create()

            val httpClient =
                createKtor(
                    httpClientEngine = androidEngine,
                    loggingHandler = Logger.EMPTY,
                    baseUrl = "http://localhost",
                )

            httpClient.engine shouldBe androidEngine
        }
}
