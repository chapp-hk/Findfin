package ch.app.hk.bank.locator.core.locale

import androidx.test.filters.SmallTest
import androidx.test.platform.app.InstrumentationRegistry
import io.kotest.matchers.shouldBe
import org.junit.Test

@SmallTest
class AppLocaleManagerTest {
    private val testContext = InstrumentationRegistry.getInstrumentation().context

    @Test
    fun test_availableLocales() {
        AppLocaleManager.availableLocales(testContext) shouldBe
            listOf(
                AppLocale(
                    displayName = "English",
                    tag = "en",
                ),
                AppLocale(
                    displayName = "中文",
                    tag = "zh",
                ),
            )
    }
}
