package org.chapp.findfin.core.locale

import java.util.Locale

interface AppLocaleRepository {
    fun setLocale(locale: String)

    fun getCurrentLocale(): Locale
}
