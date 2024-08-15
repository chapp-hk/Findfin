package ch.app.hk.bank.locator.core.locale.impl

import android.content.Context
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.os.LocaleListCompat
import ch.app.hk.bank.locator.core.locale.api.AppLocale
import ch.app.hk.bank.locator.core.locale.api.AppLocaleRepository
import ch.app.library.hiltwrap.annotation.HiltWrapBindModule
import dagger.hilt.android.qualifiers.ApplicationContext
import java.util.Locale
import javax.inject.Inject

@HiltWrapBindModule
internal class AppLocaleRepositoryImpl @Inject constructor(
    @ApplicationContext private val context: Context,
) : AppLocaleRepository {
    override fun setLocale(locale: String) {
        AppCompatDelegate.setApplicationLocales(LocaleListCompat.forLanguageTags(locale))
    }

    override fun getCurrentLocale(): Locale = AppCompatDelegate.getApplicationLocales()[0] ?: Locale.ENGLISH

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
