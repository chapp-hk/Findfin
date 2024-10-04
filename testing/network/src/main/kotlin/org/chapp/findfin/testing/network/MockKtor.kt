package org.chapp.findfin.testing.network

import androidx.annotation.RestrictTo
import androidx.annotation.RestrictTo.Scope
import io.ktor.client.engine.mock.MockEngine
import io.ktor.client.engine.mock.respond
import io.ktor.client.plugins.logging.EMPTY
import io.ktor.client.plugins.logging.Logger
import io.ktor.http.Headers
import io.ktor.http.HttpStatusCode
import io.ktor.utils.io.ByteReadChannel
import org.chapp.findfin.core.network.createKtor
import org.chapp.findfin.testing.util.readResourceAsText

@RestrictTo(Scope.TESTS)
class MockKtor(
    baseUrl: String,
    contentFilePath: String,
    status: HttpStatusCode,
    headers: Headers,
) {
    private val mockEngine =
        MockEngine { _ ->
            respond(
                content = ByteReadChannel(text = readResourceAsText(contentFilePath)),
                status = status,
                headers = headers,
            )
        }

    val mockHttpClient =
        createKtor(
            httpClientEngine = mockEngine,
            loggingHandler = Logger.EMPTY,
            baseUrl = baseUrl,
        )

    fun lastRequest() = mockEngine.requestHistory.last()
}
