package ch.app.hk.bank.locator.core.locale

import android.content.Context
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.os.LocaleListCompat
import java.util.Locale

object AppLocaleManager {
    fun setLocale(locale: String) {
        AppCompatDelegate.setApplicationLocales(LocaleListCompat.forLanguageTags(locale))
    }

    fun getCurrentLocale(): Locale? {
        return AppCompatDelegate.getApplicationLocales()[0]
    }

    fun availableLocales(context: Context) =
        listOf(
            AppLocale(
                displayName = context.getString(R.string.locale_name_en),
                tag = "en",
            ),
            AppLocale(
                displayName = context.getString(R.string.locale_name_zh),
                tag = "zh",
            ),
        )
}
