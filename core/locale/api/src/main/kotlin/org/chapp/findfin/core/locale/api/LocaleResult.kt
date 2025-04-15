package org.chapp.findfin.core.locale.api

/**
 * Represents the result of a locale-related operation.
 *
 * This sealed interface defines three possible outcomes:
 * - **Error**: Indicates that an error occurred during the operation.
 * - **Default**: Represents the default locale when no specific locale is set.
 * - **Custom**: Represents a custom locale with a specific language tag and display name.
 */
sealed interface LocaleResult {
    /**
     * Represents an error result in a locale operation.
     */
    data object Error : LocaleResult

    /**
     * Represents the default locale when no specific locale is set.
     */
    data object Default : LocaleResult

    /**
     * Represents a custom locale with a specific language tag and display name.
     *
     * @property tag The language tag of the custom locale (e.g., "en", "zh").
     * @property displayName The display name of the custom locale, localized to the language itself.
     */
    data class Custom(
        val tag: String,
        val displayName: String,
    ) : LocaleResult
}
