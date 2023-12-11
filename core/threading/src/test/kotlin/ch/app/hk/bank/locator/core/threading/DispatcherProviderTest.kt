package ch.app.hk.bank.locator.core.threading

import io.kotest.matchers.shouldBe
import kotlinx.coroutines.Dispatchers
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test

@DisplayName("DispatcherProvider unit tests")
class DispatcherProviderTest {
    private val dispatcherProvider = DispatcherProvider()

    @Test
    @DisplayName("io() should return io dispatcher")
    fun testIo() {
        dispatcherProvider.io() shouldBe Dispatchers.IO
    }

    @Test
    @DisplayName("default should return default dispatcher")
    fun testDefault() {
        dispatcherProvider.default() shouldBe Dispatchers.Default
    }
}
