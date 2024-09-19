package ch.app.hk.bank.locator.core.preferences.runtime

import kotlinx.coroutines.flow.Flow

interface PreferenceStore<T> {
    val key: String

    fun get(): Flow<T>

    suspend fun set(value: T)
}
