package org.chapp.findfin.core.preferences.provider.impl

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
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
        runCatching {
            dataStore.edit { preferences ->
                preferences[booleanPreferencesKey(key)] = value
            }
        }.getOrElse { error ->
            appLogger.error(
                tag = javaClass.simpleName,
                message = "Failed to set boolean preference for key: $key",
                throwable = error,
            )

            if (error is CancellationException) {
                throw error
            } else {
                // no-op or handle the error as needed
            }
        }
    }

    override fun getBoolean(
        key: String,
        defaultValue: Boolean,
    ): Flow<Boolean> {
        return runCatching {
            dataStore.data.map { preferences ->
                preferences[booleanPreferencesKey(key)] ?: defaultValue
            }
        }.getOrElse { error ->
            appLogger.error(
                tag = javaClass.simpleName,
                message = "Failed to get boolean preference for key: $key",
                throwable = error,
            )

            if (error is CancellationException) {
                throw error
            } else {
                flowOf(defaultValue)
            }
        }
    }

    override suspend fun setString(
        key: String,
        value: String,
    ) {
        runCatching {
            dataStore.edit { preferences ->
                preferences[stringPreferencesKey(key)] = value
            }
        }.getOrElse { error ->
            appLogger.error(
                tag = javaClass.simpleName,
                message = "Failed to set string preference for key: $key",
                throwable = error,
            )

            if (error is CancellationException) {
                throw error
            } else {
                // no-op or handle the error as needed
            }
        }
    }

    override fun getString(
        key: String,
        defaultValue: String,
    ): Flow<String> {
        return runCatching {
            dataStore.data.map { preferences ->
                preferences[stringPreferencesKey(key)] ?: defaultValue
            }
        }.getOrElse { error ->
            appLogger.error(
                tag = javaClass.simpleName,
                message = "Failed to get string preference for key: $key",
                throwable = error,
            )

            if (error is CancellationException) {
                throw error
            } else {
                flowOf(defaultValue)
            }
        }
    }
}
