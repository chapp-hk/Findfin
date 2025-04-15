package org.chapp.findfin.core.locale.api

/**
 * Interface for managing application locales.
 *
 * This interface provides methods to set and retrieve the application's current locale,
 * as well as to fetch a list of available languages supported by the application.
 */
interface LocaleProviderManager {
    /**
     * Sets the application locale to the specified language tag.
     *
     * @param locale The language tag (e.g., "en", "zh") to set the application locale to.
     */
    fun setLocale(locale: String)

    /**
     * Retrieves the current application locale.
     *
     * @return A [LocaleResult] representing the current locale. This can be a custom locale,
     * the default locale, or an error state if the locale cannot be determined.
     */
    fun getCurrentLocale(): LocaleResult

    /**
     * Retrieves the language tag of the current application locale.
     *
     * @return A [String] representing the language tag of the current locale. Returns an empty
     * string if the default locale is used or if an error occurs.
     */
    fun getCurrentLocaleTag(): String

    /**
     * Retrieves a list of available languages supported by the application.
     *
     * @return A [List] of [Language] objects, each representing a supported language option.
     */
    fun getAvailableLanguages(): List<Language>
}
