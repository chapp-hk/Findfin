package org.chapp.findfin.core.preferences.provider

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStoreFile
import androidx.test.filters.SmallTest
import androidx.test.platform.app.InstrumentationRegistry
import app.cash.turbine.test
import io.kotest.matchers.shouldBe
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.runTest
import org.junit.Test

@SmallTest
class AppPreferencesManagerImplTest {
    private val context = InstrumentationRegistry.getInstrumentation().context
    private val testScope = TestScope(StandardTestDispatcher())
    private val testDataStore: DataStore<Preferences> =
        PreferenceDataStoreFactory.create(
            scope = testScope.backgroundScope,
            produceFile = { context.preferencesDataStoreFile("test_datastore") },
        )

    private val appPreferences = AppPreferencesManagerImpl(dataStore = testDataStore)

    @Test
    fun test_initial_boolean_value() =
        testScope.runTest {
            appPreferences.getBoolean("boolean").test {
                awaitItem() shouldBe false
                cancelAndIgnoreRemainingEvents()
            }
        }

    @Test
    fun testBoolean() =
        testScope.runTest {
            appPreferences.setBoolean("boolean", true)
            appPreferences.getBoolean("boolean").test {
                awaitItem() shouldBe true
                cancelAndIgnoreRemainingEvents()
            }

            testDataStore.edit { it.clear() }
        }

    @Test
    fun test_initial_string_value() =
        testScope.runTest {
            appPreferences.getString("string").test {
                awaitItem() shouldBe ""
                cancelAndIgnoreRemainingEvents()
            }
        }

    @Test
    fun testString() =
        testScope.runTest {
            appPreferences.setString("string", "test_value")
            appPreferences.getString("string").test {
                awaitItem() shouldBe "test_value"
                cancelAndIgnoreRemainingEvents()
            }

            testDataStore.edit { it.clear() }
        }
}
