package org.chapp.findfin.feature.onboarding.presentation.ui.language.model

import io.kotest.matchers.shouldBe
import org.chapp.findfin.core.design.ui.foundation.text.UiText
import org.chapp.findfin.feature.onboarding.presentation.R
import org.chapp.findfin.feature.setting.data.repo.language.model.Language
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test

@DisplayName("SelectLanguageUiModelMapper unit tests")
class SelectLanguageUiModelMapperTest {
    private val selectLanguageUiModelMapper = SelectLanguageUiModelMapper()

    @Test
    fun `test map default`() {
        val language =
            Language(
                isDefault = true,
                name = "",
                tag = "",
            )

        selectLanguageUiModelMapper.map(language) shouldBe
            SelectLanguageUiModel(
                displayName = UiText.ResourceString(resId = R.string.onboarding_select_language_default),
                tag = "code",
            )
    }

    @Test
    fun `test map not default`() {
        val language =
            Language(
                isDefault = false,
                name = "English",
                tag = "en",
            )

        selectLanguageUiModelMapper.map(language) shouldBe
            SelectLanguageUiModel(
                displayName = UiText.ActualString(value = "English"),
                tag = "en",
            )
    }
}
