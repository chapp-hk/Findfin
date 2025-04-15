package org.chapp.findfin.core.locale.api

/**
 * Represents a language option available in the application.
 *
 * @property isDefault Indicates whether this language is the default language.
 * @property localeTag The language tag (e.g., "en", "zh") representing the locale.
 * @property displayName The display name of the language, localized to the language itself.
 */
data class Language(
    val isDefault: Boolean,
    val localeTag: String,
    val displayName: String,
)
