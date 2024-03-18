package ch.app.hk.bank.locator.testing.google.play.services.task

import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import java.lang.Exception

@DisplayName("MockTask unit tests")
class MockTaskTest {
    @Test
    fun `mockTaskError() should return non-null exception`() {
        val task = mockTaskError<Any>(Exception())

        task.exception shouldBe Exception()
    }

    @Test
    fun `mockTaskResult() should return non-null result`() {
        val task = mockTaskResult(Unit)

        task.result shouldBe Unit
    }
}
