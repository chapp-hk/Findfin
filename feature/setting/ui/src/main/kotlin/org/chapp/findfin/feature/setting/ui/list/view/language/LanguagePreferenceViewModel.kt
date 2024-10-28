package org.chapp.findfin.feature.setting.ui.list.view.language

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import org.chapp.findfin.core.locale.api.AppLocaleRepository
import java.util.Locale
import javax.inject.Inject

@HiltViewModel
class LanguagePreferenceViewModel @Inject constructor(
    private val appLocaleRepository: AppLocaleRepository,
) : ViewModel() {
    val supportedLocales = appLocaleRepository.availableLocales()

    fun getCurrentLanguageName(): String {
        val locale = appLocaleRepository.getCurrentLocale().language
        return Locale(locale).let { it.getDisplayLanguage(it) }
    }

    fun getCurrentLanguage(): String {
        return appLocaleRepository.getCurrentLocale().language
    }

    fun setLanguage(locale: String) {
        appLocaleRepository.setLocale(locale)
    }
}
