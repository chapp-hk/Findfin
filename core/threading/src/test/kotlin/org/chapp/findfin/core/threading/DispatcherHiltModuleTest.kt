package org.chapp.findfin.core.threading

import io.kotest.matchers.shouldBe
import kotlinx.coroutines.Dispatchers
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test

@DisplayName("DispatcherHiltModule unit tests")
class DispatcherHiltModuleTest {
    private val dispatcherProvider = DispatcherHiltModule()

    @Test
    @DisplayName("provideIoDispatcher() should return io dispatcher")
    fun testIo() {
        dispatcherProvider.provideIoDispatcher() shouldBe Dispatchers.IO
    }

    @Test
    @DisplayName("provideDefaultDispatcher() should return default dispatcher")
    fun testDefault() {
        dispatcherProvider.provideDefaultDispatcher() shouldBe Dispatchers.Default
    }
}
