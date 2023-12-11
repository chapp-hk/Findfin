package ch.app.hk.bank.locator.testing.util

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

fun Any.readResourceAsText(path: String): String {
    return javaClass.classLoader.getResource(path)?.readText().orEmpty()
}

inline fun <reified T> Any.readResourceAsObject(path: String): T =
    Gson().fromJson(
        readResourceAsText(path),
        object : TypeToken<T>() {}.type,
    )
