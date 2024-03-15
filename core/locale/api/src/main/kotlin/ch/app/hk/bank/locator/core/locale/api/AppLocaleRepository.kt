package ch.app.hk.bank.locator.core.locale.api

import java.util.Locale

interface AppLocaleRepository {
    fun setLocale(locale: String)

    fun getCurrentLocale(): Locale

    fun availableLocales(): List<AppLocale>
}
