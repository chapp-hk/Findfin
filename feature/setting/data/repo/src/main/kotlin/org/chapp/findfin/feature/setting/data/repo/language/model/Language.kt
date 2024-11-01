package org.chapp.findfin.feature.setting.data.repo.language.model

/**
 * Data class representing a language.
 *
 * @property isDefault Indicates if this language is the default language.
 * @property name The display name of the language.
 * @property tag The language tag.
 */
data class Language(
    val isDefault: Boolean,
    val name: String,
    val tag: String,
)
