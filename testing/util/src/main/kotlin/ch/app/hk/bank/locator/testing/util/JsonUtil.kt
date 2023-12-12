package ch.app.hk.bank.locator.testing.util

import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json

@OptIn(ExperimentalSerializationApi::class)
val json =
    Json {
        coerceInputValues = true
        encodeDefaults = true
        ignoreUnknownKeys = true
        explicitNulls = false
    }
