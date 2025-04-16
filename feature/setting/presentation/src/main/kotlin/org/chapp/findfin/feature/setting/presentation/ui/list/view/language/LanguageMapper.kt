package org.chapp.findfin.feature.setting.presentation.ui.list.view.language

import org.chapp.findfin.core.design.ui.foundation.text.UiText
import org.chapp.findfin.core.locale.api.Language
import org.chapp.findfin.feature.setting.presentation.R

internal class LanguageMapper {
    fun map(language: Language): LanguagePreferenceItem {
        return LanguagePreferenceItem(
            title =
                if (language.isDefault) {
                    UiText.ResourceString(resId = R.string.setting_theme_summary_system)
                } else {
                    UiText.ActualString(language.displayName)
                },
            value = language.localeTag,
        )
    }
}
