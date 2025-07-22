package org.chapp.findfin.core.preferences.provider.impl

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import org.chapp.findfin.core.logging.appLogger
import org.chapp.findfin.core.preferences.provider.api.AppPreferencesManager
import org.chapp.library.hiltwrap.annotation.HiltWrapBindModule
import javax.inject.Inject

@HiltWrapBindModule
internal class AppPreferencesManagerImpl @Inject constructor(
    private val dataStore: DataStore<Preferences>,
) : AppPreferencesManager {
    override suspend fun setBoolean(
        key: String,
        value: Boolean,
    ) {
        setPreference(key = booleanPreferencesKey(name = key), value = value)
    }

    override fun getBoolean(
        key: String,
        defaultValue: Boolean,
    ): Flow<Boolean> {
        return getPreference(key = booleanPreferencesKey(name = key), defaultValue = defaultValue)
    }

    override suspend fun setString(
        key: String,
        value: String,
    ) {
        setPreference(stringPreferencesKey(key), value)
    }

    override fun getString(
        key: String,
        defaultValue: String,
    ): Flow<String> {
        return getPreference(stringPreferencesKey(key), defaultValue)
    }

    private fun <T> getPreference(
        key: Preferences.Key<T>,
        defaultValue: T,
    ): Flow<T> {
        return dataStore.data
            .map { preferences ->
                preferences[key] ?: defaultValue
            }
            .catch { error ->
                appLogger.error(
                    tag = javaClass.simpleName,
                    message = "Failed to get preference for key: ${key.name}",
                    throwable = error,
                )

                if (error is CancellationException) {
                    throw error
                } else {
                    emit(defaultValue)
                }
            }
    }

    private suspend fun <T> setPreference(
        key: Preferences.Key<T>,
        value: T,
    ) {
        runCatching {
            dataStore.edit { preferences ->
                preferences[key] = value
            }
        }.getOrElse { error ->
            appLogger.error(
                tag = javaClass.simpleName,
                message = "Failed to set preference for key: ${key.name}",
                throwable = error,
            )

            if (error is CancellationException) {
                throw error
            }
        }
    }
}
