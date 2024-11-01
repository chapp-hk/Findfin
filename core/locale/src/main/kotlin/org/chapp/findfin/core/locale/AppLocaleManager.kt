package org.chapp.findfin.core.locale

import java.util.Locale

/**
 * Interface for managing application locales.
 */
interface AppLocaleManager {
    /**
     * Sets the application locale to the specified language tag.
     *
     * @param locale The language tag to set the application locale to.
     */
    fun setLocale(locale: String)

    /**
     * Retrieves the current application locale.
     *
     * @return The current [Locale], or null if no locale is set.
     */
    fun getCurrentLocale(): Locale?
}
