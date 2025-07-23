package org.chapp.findfin.core.preferences.provider.api

import kotlinx.coroutines.flow.Flow

/**
 * Interface for managing application preferences.
 *
 * This interface provides methods to set and get boolean and string preferences.
 */
interface AppPreferencesManager {
    /**
     * Sets a boolean value in the preferences.
     *
     * @param key The key for the preference.
     * @param value The boolean value to be set.
     */
    suspend fun setBoolean(
        key: String,
        value: Boolean,
    )

    /**
     * Retrieves a boolean value from the preferences.
     *
     * @param key The key for the preference.
     * @param defaultValue The default value to return if the key does not exist.
     * @return A [Flow] emitting the boolean value associated with the key.
     */
    fun getBoolean(
        key: String,
        defaultValue: Boolean,
    ): Flow<Boolean>

    /**
     * Sets a string value in the preferences.
     *
     * @param key The key for the preference.
     * @param value The string value to be set.
     */
    suspend fun setString(
        key: String,
        value: String,
    )

    /**
     * Retrieves a string value from the preferences.
     *
     * @param key The key for the preference.
     * @param defaultValue The default value to return if the key does not exist.
     * @return A [Flow] emitting the string value associated with the key.
     */
    fun getString(
        key: String,
        defaultValue: String,
    ): Flow<String>
}
