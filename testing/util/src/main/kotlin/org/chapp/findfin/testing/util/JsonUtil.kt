package org.chapp.findfin.testing.util

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
