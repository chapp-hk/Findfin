package ch.app.hk.bank.locator.core.network

import io.ktor.client.HttpClient
import io.ktor.client.engine.HttpClientEngine
import io.ktor.client.plugins.BrowserUserAgent
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.plugins.resources.Resources
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json

@OptIn(ExperimentalSerializationApi::class)
fun createKtor(
    httpClientEngine: HttpClientEngine,
    loggingHandler: Logger,
    baseUrl: String,
) = HttpClient(httpClientEngine) {
    BrowserUserAgent()

    install(ContentNegotiation) {
        json(
            Json {
                coerceInputValues = true
                encodeDefaults = true
                ignoreUnknownKeys = true
                explicitNulls = false
            },
        )
    }

    Logging {
        level = LogLevel.ALL
        logger = loggingHandler
    }

    defaultRequest {
        url(baseUrl)
    }

    install(Resources)
}
