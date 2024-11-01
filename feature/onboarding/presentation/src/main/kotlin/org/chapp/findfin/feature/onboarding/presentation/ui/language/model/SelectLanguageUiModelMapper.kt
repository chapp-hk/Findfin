package org.chapp.findfin.feature.onboarding.presentation.ui.language.model

import org.chapp.findfin.core.design.ui.foundation.text.UiText
import org.chapp.findfin.feature.onboarding.presentation.R
import org.chapp.findfin.feature.setting.data.repo.language.model.Language

internal class SelectLanguageUiModelMapper {
    fun map(language: Language): SelectLanguageUiModel {
        return SelectLanguageUiModel(
            displayName =
                if (language.isDefault) {
                    UiText.ResourceString(resId = R.string.onboarding_select_language_default)
                } else {
                    UiText.ActualString(value = language.name)
                },
            tag = language.tag,
        )
    }
}
