package org.chapp.findfin.feature.onboarding.presentation.ui.language.model

import org.chapp.findfin.core.locale.api.AppLocale
import org.mapstruct.Mapper

@Mapper
internal interface SelectLanguageUiModelMapper {
    fun clone(appLocale: AppLocale): SelectLanguageUiModel
}
