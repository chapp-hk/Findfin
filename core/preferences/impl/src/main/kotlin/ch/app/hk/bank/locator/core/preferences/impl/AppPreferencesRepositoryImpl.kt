package ch.app.hk.bank.locator.core.preferences.impl

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import ch.app.framework.hiltext.annotation.HiltExtBindModule
import ch.app.hk.bank.locator.core.preferences.api.AppPreferencesRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

@HiltExtBindModule
internal class AppPreferencesRepositoryImpl @Inject constructor(
    private val dataStore: DataStore<Preferences>,
) : AppPreferencesRepository {
    override suspend fun setBoolean(key: String, value: Boolean) {
        dataStore.edit {preferences ->
            preferences[booleanPreferencesKey(key)] = value
        }
    }

    override fun getBoolean(key: String): Flow<Boolean> {
        return dataStore.data.map { preferences ->
            preferences[booleanPreferencesKey(key)] ?: false
        }
    }
}
