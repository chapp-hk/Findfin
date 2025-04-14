package org.chapp.findfin.core.locale.impl

import androidx.appcompat.app.AppCompatDelegate
import androidx.core.os.LocaleListCompat
import org.chapp.findfin.core.locale.api.AppLocaleManager
import org.chapp.library.hiltwrap.annotation.HiltWrapBindModule
import java.util.Locale
import javax.inject.Inject

@HiltWrapBindModule
internal class AppLocaleManagerImpl @Inject constructor() : AppLocaleManager {
    override fun setLocale(locale: String) {
        AppCompatDelegate.setApplicationLocales(LocaleListCompat.forLanguageTags(locale))
    }

    override fun getCurrentLocale(): Locale? = AppCompatDelegate.getApplicationLocales()[0]
}
