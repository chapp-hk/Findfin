package ch.app.hk.bank.locator.core.preferences.storage

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

internal val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "preferences_datastore")

@Module
@InstallIn(SingletonComponent::class)
internal class DataStoreHiltModule {
    @Provides
    fun provideDataStore(
        @ApplicationContext context: Context,
    ) = context.dataStore
}
