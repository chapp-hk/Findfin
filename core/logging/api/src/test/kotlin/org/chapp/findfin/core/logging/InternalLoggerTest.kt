package org.chapp.findfin.core.logging

import co.touchlab.kermit.ExperimentalKermitApi
import co.touchlab.kermit.Logger
import co.touchlab.kermit.Severity
import co.touchlab.kermit.TestConfig
import co.touchlab.kermit.TestLogWriter
import io.kotest.matchers.shouldBe
import io.mockk.spyk
import org.junit.jupiter.api.Test

@OptIn(ExperimentalKermitApi::class)
class InternalLoggerTest {
    private val testLogWriter = TestLogWriter(loggable = Severity.Verbose)

    private val kermit =
        spyk(
            Logger(
                TestConfig(
                    minSeverity = Severity.Verbose,
                    logWriterList = listOf(testLogWriter),
                ),
            ),
        )

    private val internalLogger = InternalLogger(logger = kermit)

    @Test
    fun `test verbose logging`() {
        val tag = "tag"
        val message = "message"
        val throwable = Throwable()

        internalLogger.verbose(tag, message, throwable)

        testLogWriter.logs.first().let {
            it.message shouldBe message
            it.tag shouldBe tag
            it.throwable shouldBe throwable
        }
    }

    @Test
    fun `test debug logging`() {
        val tag = "tag"
        val message = "message"
        val throwable = Throwable()

        internalLogger.debug(tag, message, throwable)

        testLogWriter.logs.first().let {
            it.message shouldBe message
            it.tag shouldBe tag
            it.throwable shouldBe throwable
        }
    }

    @Test
    fun `test info logging`() {
        val tag = "tag"
        val message = "message"
        val throwable = Throwable()

        internalLogger.info(tag, message, throwable)

        testLogWriter.logs.first().let {
            it.message shouldBe message
            it.tag shouldBe tag
            it.throwable shouldBe throwable
        }
    }

    @Test
    fun `test warn logging`() {
        val tag = "tag"
        val message = "message"
        val throwable = Throwable()

        internalLogger.warn(tag, message, throwable)

        testLogWriter.logs.first().let {
            it.message shouldBe message
            it.tag shouldBe tag
            it.throwable shouldBe throwable
        }
    }

    @Test
    fun `test error logging`() {
        val tag = "tag"
        val message = "message"
        val throwable = Throwable()

        internalLogger.error(tag, message, throwable)

        testLogWriter.logs.first().let {
            it.message shouldBe message
            it.tag shouldBe tag
            it.throwable shouldBe throwable
        }
    }

    @Test
    fun `test assert logging`() {
        val tag = "tag"
        val message = "message"
        val throwable = Throwable()

        internalLogger.assert(tag, message, throwable)

        testLogWriter.logs.first().let {
            it.message shouldBe message
            it.tag shouldBe tag
            it.throwable shouldBe throwable
        }
    }
}
