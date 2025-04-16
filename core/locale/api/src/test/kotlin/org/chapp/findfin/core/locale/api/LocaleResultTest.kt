package org.chapp.findfin.core.locale.api

import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test

@DisplayName("LocaleResult unit tests")
class LocaleResultTest {
    @Test
    fun `test Custom fields`() {
        val custom =
            LocaleResult.Custom(
                tag = "en",
                displayName = "English",
            )

        custom.tag shouldBe "en"
        custom.displayName shouldBe "English"
    }
}
