package ch.app.hk.bank.locator.core.locale.impl

import android.content.Context
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.os.LocaleListCompat
import ch.app.framework.hiltext.annotation.HiltExtBindModule
import ch.app.hk.bank.locator.core.locale.api.AppLocale
import ch.app.hk.bank.locator.core.locale.api.AppLocaleRepository
import dagger.hilt.android.qualifiers.ApplicationContext
import java.util.Locale
import javax.inject.Inject

@HiltExtBindModule
class AppLocaleRepositoryImpl @Inject constructor(
    @ApplicationContext private val context: Context,
) : AppLocaleRepository {
    override fun setLocale(locale: String) {
        AppCompatDelegate.setApplicationLocales(LocaleListCompat.forLanguageTags(locale))
    }

    override fun getCurrentLocale(): Locale? =
        AppCompatDelegate.getApplicationLocales()[0]

    override fun availableLocales(): List<AppLocale> =
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
