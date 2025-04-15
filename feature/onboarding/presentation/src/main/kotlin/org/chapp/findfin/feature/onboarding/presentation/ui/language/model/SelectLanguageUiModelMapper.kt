package org.chapp.findfin.feature.onboarding.presentation.ui.language.model

import org.chapp.findfin.core.design.ui.foundation.text.UiText
import org.chapp.findfin.core.locale.api.Language
import org.chapp.findfin.feature.onboarding.presentation.R

internal class SelectLanguageUiModelMapper {
    fun map(language: Language): SelectLanguageUiModel {
        return SelectLanguageUiModel(
            displayName =
                if (language.isDefault) {
                    UiText.ResourceString(resId = R.string.onboarding_select_language_default)
                } else {
                    UiText.ActualString(value = language.displayName)
                },
            tag = language.localeTag,
        )
    }
}
