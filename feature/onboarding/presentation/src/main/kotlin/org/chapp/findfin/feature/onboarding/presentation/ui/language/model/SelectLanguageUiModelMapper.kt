package org.chapp.findfin.feature.onboarding.presentation.ui.language.model

import org.chapp.findfin.feature.setting.data.repo.language.model.Language
import org.mapstruct.Mapper
import org.mapstruct.Mapping

@Mapper
internal interface SelectLanguageUiModelMapper {
    @Mapping(source = "name", target = "displayName")
    fun clone(language: Language): SelectLanguageUiModel
}
