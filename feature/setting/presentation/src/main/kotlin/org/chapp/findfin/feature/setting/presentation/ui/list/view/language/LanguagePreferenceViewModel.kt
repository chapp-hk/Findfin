package org.chapp.findfin.feature.setting.presentation.ui.list.view.language

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import org.chapp.findfin.core.design.ui.foundation.text.UiText
import org.chapp.findfin.core.locale.AppLocaleManager
import org.chapp.findfin.feature.setting.data.repo.language.repository.LanguageRepository
import org.chapp.findfin.feature.setting.presentation.R
import java.util.Locale
import javax.inject.Inject

@HiltViewModel
internal class LanguagePreferenceViewModel @Inject constructor(
    languageRepository: LanguageRepository,
    private val appLocaleManager: AppLocaleManager,
) : ViewModel() {
    private val languageMapper = LanguageMapper()

    val availableLanguages: List<LanguagePreferenceItem> =
        languageRepository.getAvailableLanguages().map(languageMapper::map)

    fun getCurrentLanguageName(): UiText {
        val locale = appLocaleManager.getCurrentLocale()?.language
        return if (locale == null) {
            UiText.ResourceString(R.string.setting_theme_summary_system)
        } else {
            UiText.ActualString(Locale(locale).let { it.getDisplayLanguage(it) })
        }
    }

    fun getCurrentLanguageTag(): String {
        return appLocaleManager.getCurrentLocale()?.language.orEmpty()
    }

    fun setLanguage(locale: String) {
        appLocaleManager.setLocale(locale)
    }
}
