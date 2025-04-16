package org.chapp.findfin.feature.setting.presentation.ui.list.view.language

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import org.chapp.findfin.core.design.ui.foundation.text.UiText
import org.chapp.findfin.core.locale.api.LocaleProviderManager
import org.chapp.findfin.core.locale.api.LocaleResult
import org.chapp.findfin.feature.setting.presentation.R
import javax.inject.Inject

@HiltViewModel
internal class LanguagePreferenceViewModel @Inject constructor(
    private val localeProviderManager: LocaleProviderManager,
) : ViewModel() {
    private val languageMapper = LanguageMapper()

    val availableLanguages: List<LanguagePreferenceItem> =
        localeProviderManager.getAvailableLanguages().map(languageMapper::map)

    fun getCurrentLanguageName(): UiText {
        return when (val localeResult = localeProviderManager.getCurrentLocale()) {
            is LocaleResult.Custom -> {
                UiText.ActualString(localeResult.displayName)
            }

            is LocaleResult.Default,
            is LocaleResult.Error,
            -> {
                UiText.ResourceString(R.string.setting_theme_summary_system)
            }
        }
    }

    fun getCurrentLanguageTag(): String {
        return localeProviderManager.getCurrentLocaleTag()
    }

    fun setLanguage(locale: String) {
        localeProviderManager.setLocale(locale)
    }
}
