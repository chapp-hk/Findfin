package ch.app.hk.bank.locator.core.logging

val appLogger: AppLogger = InternalLogger()

interface AppLogger {
    fun verbose(
        tag: String = "",
        message: String,
        throwable: Throwable? = null,
    )

    fun debug(
        tag: String = "",
        message: String,
        throwable: Throwable? = null,
    )

    fun info(
        tag: String = "",
        message: String,
        throwable: Throwable? = null,
    )

    fun warn(
        tag: String = "",
        message: String,
        throwable: Throwable? = null,
    )

    fun error(
        tag: String = "",
        message: String,
        throwable: Throwable? = null,
    )

    fun assert(
        tag: String = "",
        message: String,
        throwable: Throwable? = null,
    )
}
