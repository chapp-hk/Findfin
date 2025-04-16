package org.chapp.findfin.feature.setting.presentation.ui.list.view.language

import io.kotest.matchers.shouldBe
import org.chapp.findfin.core.design.ui.foundation.text.UiText
import org.chapp.findfin.core.locale.api.Language
import org.chapp.findfin.feature.setting.presentation.R
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test

@DisplayName("LanguageMapper unit tests")
class LanguageMapperTest {
    @Test
    fun `should map default language correctly`() {
        // Arrange
        val language = Language(isDefault = true, displayName = "System", localeTag = "system")
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
        val language = Language(isDefault = false, displayName = "English", localeTag = "en")
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
