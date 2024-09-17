package ch.app.hk.bank.locator.core.preferences.storage

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import ch.app.library.hiltwrap.annotation.HiltWrapBindModule
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

@HiltWrapBindModule
internal class AppPreferencesManagerImpl @Inject constructor(
    private val dataStore: DataStore<Preferences>,
) : AppPreferencesManager {
    override suspend fun setBoolean(
        key: String,
        value: Boolean,
    ) {
        dataStore.edit { preferences ->
            preferences[booleanPreferencesKey(key)] = value
        }
    }

    override fun getBoolean(key: String): Flow<Boolean> {
        return dataStore.data.map { preferences ->
            preferences[booleanPreferencesKey(key)] ?: false
        }
    }

    override suspend fun setString(
        key: String,
        value: String,
    ) {
        dataStore.edit { preferences ->
            preferences[stringPreferencesKey(key)] = value
        }
    }

    override fun getString(key: String): Flow<String> {
        return dataStore.data.map { preferences ->
            preferences[stringPreferencesKey(key)].orEmpty()
        }
    }
}
