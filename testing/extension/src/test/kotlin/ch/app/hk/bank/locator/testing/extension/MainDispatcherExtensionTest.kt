package ch.app.hk.bank.locator.testing.extension

import app.cash.turbine.test
import io.kotest.matchers.shouldBe
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

@ExtendWith(MainDispatcherExtension::class)
@DisplayName("MainDispatcherExtension unit tests")
class MainDispatcherExtensionTest {
    private val flow = flowOf(0, 1, 2).flowOn(Dispatchers.Default)

    @Test
    fun `test MainDispatcherExtension`() =
        runTest {
            flow.test {
                awaitItem() shouldBe 0
                awaitItem() shouldBe 1
                awaitItem() shouldBe 2
                awaitComplete()
            }
        }
}
