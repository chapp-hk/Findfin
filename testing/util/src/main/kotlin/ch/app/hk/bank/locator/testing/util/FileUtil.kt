package ch.app.hk.bank.locator.testing.util

fun Any.readResourceAsText(path: String): String {
    return javaClass.classLoader.getResource(path)?.readText().orEmpty()
}

inline fun <reified T> Any.readResourceAsJson(path: String): T = json.decodeFromString(readResourceAsText(path))
