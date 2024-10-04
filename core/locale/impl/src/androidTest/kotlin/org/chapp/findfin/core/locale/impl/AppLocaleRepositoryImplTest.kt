package org.chapp.findfin.core.locale.impl

import androidx.test.platform.app.InstrumentationRegistry
import io.kotest.matchers.shouldBe
import org.chapp.findfin.core.locale.api.AppLocale
import org.junit.Test

class AppLocaleRepositoryImplTest {
    private val testContext = InstrumentationRegistry.getInstrumentation().context

    private val appLocaleRepository =
        AppLocaleRepositoryImpl(
            context = testContext,
        )

    @Test
    fun test_availableLocales() {
        appLocaleRepository.availableLocales() shouldBe
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
