package org.chapp.findfin.feature.setting.ui.list.view.language

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import org.chapp.findfin.core.locale.AppLocaleManager
import org.chapp.findfin.feature.setting.data.repo.language.repository.LanguageRepository
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

    fun getCurrentLanguageName(): String {
        val locale = appLocaleManager.getCurrentLocale().language
        return Locale(locale).let { it.getDisplayLanguage(it) }
    }

    fun getCurrentLanguage(): String {
        return appLocaleManager.getCurrentLocale().language
    }

    fun setLanguage(locale: String) {
        appLocaleManager.setLocale(locale)
    }
}
