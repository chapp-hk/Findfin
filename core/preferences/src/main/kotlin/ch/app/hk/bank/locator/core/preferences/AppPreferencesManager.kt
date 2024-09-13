package ch.app.hk.bank.locator.core.preferences

import kotlinx.coroutines.flow.Flow

interface AppPreferencesManager {
    suspend fun setBoolean(
        key: String,
        value: Boolean,
    )

    fun getBoolean(key: String): Flow<Boolean>
}
