package org.chapp.findfin.testing.util

import androidx.annotation.RestrictTo
import androidx.annotation.RestrictTo.Scope

@RestrictTo(Scope.TESTS)
fun Any.readResourceAsText(path: String): String {
    return javaClass.classLoader.getResource(path)?.readText().orEmpty()
}

@RestrictTo(Scope.TESTS)
inline fun <reified T> Any.readResourceAsJson(path: String): T = json.decodeFromString(readResourceAsText(path))
