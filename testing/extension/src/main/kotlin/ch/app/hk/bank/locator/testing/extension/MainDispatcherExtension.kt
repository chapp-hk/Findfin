package ch.app.hk.bank.locator.testing.extension

import androidx.annotation.RestrictTo
import androidx.annotation.RestrictTo.Scope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.jupiter.api.extension.AfterEachCallback
import org.junit.jupiter.api.extension.BeforeEachCallback
import org.junit.jupiter.api.extension.ExtensionContext

@RestrictTo(Scope.TESTS)
class MainDispatcherExtension : BeforeEachCallback, AfterEachCallback {
    private val dispatcher = StandardTestDispatcher(TestScope().testScheduler)

    override fun beforeEach(context: ExtensionContext) {
        Dispatchers.setMain(dispatcher)
    }

    override fun afterEach(context: ExtensionContext) {
        Dispatchers.resetMain()
    }
}
