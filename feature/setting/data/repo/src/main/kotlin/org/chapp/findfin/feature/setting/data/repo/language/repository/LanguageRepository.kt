package org.chapp.findfin.feature.setting.data.repo.language.repository

import org.chapp.findfin.feature.setting.data.repo.language.model.Language

/**
 * Repository interface for managing languages.
 */
interface LanguageRepository {
    /**
     * Retrieves a list of available languages.
     *
     * @return A list of [Language] objects representing the available languages.
     */
    fun getAvailableLanguages(): List<Language>
}
