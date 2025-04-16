package org.chapp.findfin.core.locale.impl

import androidx.appcompat.app.AppCompatDelegate
import androidx.core.os.LocaleListCompat
import org.chapp.findfin.core.locale.api.Language
import org.chapp.findfin.core.locale.api.LocaleProviderManager
import org.chapp.findfin.core.locale.api.LocaleResult
import org.chapp.library.hiltwrap.annotation.HiltWrapBindModule
import java.util.Locale
import javax.inject.Inject

@HiltWrapBindModule
internal class LocaleProviderManagerImpl @Inject constructor() : LocaleProviderManager {
    override fun setLocale(locale: String) {
        AppCompatDelegate.setApplicationLocales(LocaleListCompat.forLanguageTags(locale))
    }

    override fun getCurrentLocale(): LocaleResult {
        return runCatching {
            val appLocale = AppCompatDelegate.getApplicationLocales()[0]
            if (appLocale == null) {
                LocaleResult.Default
            } else {
                LocaleResult.Custom(
                    tag = appLocale.language,
                    displayName = appLocale.getDisplayLanguage(appLocale),
                )
            }
        }.getOrElse {
            LocaleResult.Error
        }
    }

    override fun getCurrentLocaleTag(): String {
        return when (val localeResult = getCurrentLocale()) {
            is LocaleResult.Custom -> localeResult.tag
            LocaleResult.Default -> ""
            LocaleResult.Error -> ""
        }
    }

    override fun getAvailableLanguages(): List<Language> {
        return listOf("", "en", "zh").map { languageTag ->
            Language(
                isDefault = languageTag.isEmpty(),
                localeTag = languageTag,
                displayName = Locale.forLanguageTag(languageTag).let { it.getDisplayName(it) },
            )
        }
    }
}
