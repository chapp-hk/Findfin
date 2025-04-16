package org.chapp.findfin.core.locale.api

import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test

@DisplayName("Language unit tests")
class LanguageTest {
    @Test
    fun `test fields`() {
        val language =
            Language(
                isDefault = true,
                localeTag = "en",
                displayName = "English",
            )

        language.isDefault shouldBe true
        language.localeTag shouldBe "en"
        language.displayName shouldBe "English"
    }
}
