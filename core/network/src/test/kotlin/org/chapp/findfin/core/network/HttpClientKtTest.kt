package org.chapp.findfin.core.network

import io.kotest.matchers.shouldNotBe
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Test

class HttpClientKtTest {
    @Test
    fun `createKtor test`() =
        runTest {
            val httpClient = HttpClientProvider("localhost").provide()

            // TODO - find a way to test the base url
            httpClient shouldNotBe null
        }
}
