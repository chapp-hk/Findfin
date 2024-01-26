package ch.app.hk.bank.locator.core.preferences.api

import kotlinx.coroutines.flow.Flow

interface AppPreferencesRepository {
    suspend fun setBoolean(
        key: String,
        value: Boolean,
    )

    fun getBoolean(key: String): Flow<Boolean?>
}
