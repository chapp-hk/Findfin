package ch.app.hk.bank.locator.core.preferences.impl

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import ch.app.framework.hiltext.annotation.HiltExtBindModule
import ch.app.hk.bank.locator.core.preferences.api.AppPreferences
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

@HiltExtBindModule
internal class AppPreferencesImpl @Inject constructor(
    private val dataStore: DataStore<Preferences>,
    private val preferenceKey: PreferenceKey,
) : AppPreferences {
    override suspend fun setLocale(locale: String) {
        dataStore.edit { preferences ->
            preferences[preferenceKey.appLocale] = locale
        }
    }

    override fun getLocale(): Flow<String?> {
        return dataStore.data.map { preferences ->
            preferences[preferenceKey.appLocale]
        }
    }
}
