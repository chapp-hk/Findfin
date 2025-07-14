package org.chapp.findfin.core.preferences.provider

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.test.core.app.ApplicationProvider
import io.kotest.matchers.types.shouldBeInstanceOf
import org.junit.Test

class DataStoreTest {
    private val context = ApplicationProvider.getApplicationContext<Context>()

    @Test
    fun testDataStoreType() {
        context.dataStore.shouldBeInstanceOf<DataStore<Preferences>>()
    }
}
