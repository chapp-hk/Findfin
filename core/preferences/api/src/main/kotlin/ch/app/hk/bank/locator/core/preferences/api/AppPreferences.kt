package ch.app.hk.bank.locator.core.preferences.api

import kotlinx.coroutines.flow.Flow

interface AppPreferences {
    suspend fun setLocale(locale: String)

    fun getLocale(): Flow<String?>
}
