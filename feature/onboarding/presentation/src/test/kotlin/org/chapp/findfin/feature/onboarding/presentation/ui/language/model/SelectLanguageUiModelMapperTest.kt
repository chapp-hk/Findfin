package org.chapp.findfin.feature.onboarding.presentation.ui.language.model

import io.kotest.matchers.shouldBe
import org.chapp.findfin.feature.setting.data.repo.language.model.Language
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.mapstruct.factory.Mappers

@DisplayName("SelectLanguageUiModelMapper unit tests")
class SelectLanguageUiModelMapperTest {
    private val selectLanguageUiModelMapper =
        Mappers.getMapper(SelectLanguageUiModelMapper::class.java)

    @Test
    fun `test clone`() {
        val language =
            Language(
                name = "name",
                tag = "code",
            )

        selectLanguageUiModelMapper.clone(language) shouldBe
            SelectLanguageUiModel(
                displayName = "name",
                tag = "code",
            )
    }
}
