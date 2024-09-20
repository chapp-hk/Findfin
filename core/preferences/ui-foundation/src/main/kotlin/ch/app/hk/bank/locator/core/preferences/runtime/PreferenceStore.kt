package ch.app.hk.bank.locator.core.preferences.runtime

import kotlinx.coroutines.flow.Flow

/**
 * Interface representing a preference store that can get and set preference values.
 *
 * @param T The type of the preference value.
 */
interface PreferenceStore<T> {
    /**
     * Retrieves the current preference value as a [Flow].
     *
     * @return A [Flow] emitting the current preference value.
     */
    fun get(): Flow<T>

    /**
     * Sets a new preference value.
     *
     * @param value The new value to be set.
     */
    suspend fun set(value: T)
}
