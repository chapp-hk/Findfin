package org.chapp.findfin.core.locale

import androidx.appcompat.app.AppCompatDelegate
import androidx.core.os.LocaleListCompat
import org.chapp.library.hiltwrap.annotation.HiltWrapBindModule
import java.util.Locale
import javax.inject.Inject

@HiltWrapBindModule
internal class AppLocaleRepositoryImpl @Inject constructor() : AppLocaleRepository {
    override fun setLocale(locale: String) {
        AppCompatDelegate.setApplicationLocales(LocaleListCompat.forLanguageTags(locale))
    }

    override fun getCurrentLocale(): Locale = AppCompatDelegate.getApplicationLocales()[0] ?: Locale.getDefault()
}
