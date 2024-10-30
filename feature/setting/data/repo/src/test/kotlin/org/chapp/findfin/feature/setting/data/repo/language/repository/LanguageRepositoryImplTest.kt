package org.chapp.findfin.feature.setting.data.repo.language.repository

import io.kotest.matchers.shouldBe
import org.chapp.findfin.feature.setting.data.repo.language.model.Language
import org.junit.jupiter.api.Test

class LanguageRepositoryImplTest {
    private val languageRepositoryImpl = LanguageRepositoryImpl()

    @Test
    fun `getAvailableLanguages should return list of available languages`() {
        languageRepositoryImpl.getAvailableLanguages() shouldBe
            listOf(
                Language(name = "English", tag = "en"),
                Language(name = "中文", tag = "zh"),
            )
    }
}
