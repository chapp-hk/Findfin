package ch.app.hk.bank.locator.core.logging

import co.touchlab.kermit.Logger

internal class InternalLogger(
    private val logger: Logger = Logger,
) : AppLogger {
    override fun verbose(
        tag: String,
        message: String,
        throwable: Throwable?,
    ) {
        logger.v(tag = tag, messageString = message, throwable = throwable)
    }

    override fun debug(
        tag: String,
        message: String,
        throwable: Throwable?,
    ) {
        logger.d(tag = tag, messageString = message, throwable = throwable)
    }

    override fun info(
        tag: String,
        message: String,
        throwable: Throwable?,
    ) {
        logger.i(tag = tag, messageString = message, throwable = throwable)
    }

    override fun warn(
        tag: String,
        message: String,
        throwable: Throwable?,
    ) {
        logger.w(tag = tag, messageString = message, throwable = throwable)
    }

    override fun error(
        tag: String,
        message: String,
        throwable: Throwable?,
    ) {
        logger.e(tag = tag, messageString = message, throwable = throwable)
    }

    override fun assert(
        tag: String,
        message: String,
        throwable: Throwable?,
    ) {
        logger.a(tag = tag, messageString = message, throwable = throwable)
    }
}
