package org.chapp.findfin.feature.onboarding.ui.language.model

import org.chapp.findfin.core.locale.api.AppLocale
import org.mapstruct.Mapper

@Mapper
interface SelectLanguageUiModelMapper {
    fun clone(appLocale: AppLocale): SelectLanguageUiModel
}
