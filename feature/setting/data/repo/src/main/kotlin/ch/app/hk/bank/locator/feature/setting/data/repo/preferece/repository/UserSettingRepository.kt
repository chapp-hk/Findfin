package ch.app.hk.bank.locator.feature.setting.data.repo.preferece.repository

import ch.app.hk.bank.locator.feature.setting.data.repo.preferece.model.Theme
import kotlinx.coroutines.flow.Flow

/**
 * Repository interface for managing user settings.
 */
interface UserSettingRepository {
    /**
     * Sets the language preference for the user.
     *
     * @param language The language code to set as the user's preference.
     */
    suspend fun setLanguagePreference(language: String)

    /**
     * Retrieves the language preference of the user.
     *
     * @return A [Flow] emitting the current language preference.
     */
    fun getLanguagePreference(): Flow<String>

    /**
     * Sets the theme preference for the user.
     *
     * @param theme The [Theme] to set as the user's preference.
     */
    suspend fun setThemePreference(theme: Theme)

    /**
     * Retrieves the theme preference of the user.
     *
     * @return A [Flow] emitting the current theme preference.
     */
    fun getThemePreference(): Flow<Theme>
}
