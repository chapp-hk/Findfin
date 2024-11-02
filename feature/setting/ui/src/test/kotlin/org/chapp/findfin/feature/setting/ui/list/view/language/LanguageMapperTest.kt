package org.chapp.findfin.feature.setting.ui.list.view.language

import io.kotest.matchers.shouldBe
import org.chapp.findfin.core.design.ui.foundation.text.UiText
import org.chapp.findfin.feature.setting.data.repo.language.model.Language
import org.chapp.findfin.feature.setting.ui.R
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test

@DisplayName("LanguageMapper unit tests")
class LanguageMapperTest {
    @Test
    fun `should map default language correctly`() {
        // Arrange
        val language = Language(isDefault = true, name = "System", tag = "system")
        val mapper = LanguageMapper()

        // Act
        val result = mapper.map(language)

        // Assert
        result shouldBe
            LanguagePreferenceItem(
                title = UiText.ResourceString(R.string.setting_theme_summary_system),
                value = "system",
            )
    }

    @Test
    fun `should map non-default language correctly`() {
        // Arrange
        val language = Language(isDefault = false, name = "English", tag = "en")
        val mapper = LanguageMapper()

        // Act
        val result = mapper.map(language)

        // Assert
        result shouldBe
            LanguagePreferenceItem(
                title = UiText.ActualString("English"),
                value = "en",
            )
    }
}
