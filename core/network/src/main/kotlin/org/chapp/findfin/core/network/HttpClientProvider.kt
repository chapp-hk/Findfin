package org.chapp.findfin.core.network

import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import io.ktor.client.HttpClient
import io.ktor.client.engine.android.Android
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.SIMPLE

@AssistedFactory
interface HttpClientFactory {
    fun create(baseUrl: String): HttpClientProvider
}

class HttpClientProvider
    @AssistedInject
    constructor(
        @Assisted private val baseUrl: String,
    ) {
        fun provide(): HttpClient {
            return createKtor(
                httpClientEngine = Android.create(),
                loggingHandler = Logger.SIMPLE,
                baseUrl = baseUrl,
            )
        }
    }
