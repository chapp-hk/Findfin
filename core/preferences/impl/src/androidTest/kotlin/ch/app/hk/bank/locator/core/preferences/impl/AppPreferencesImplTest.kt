package ch.app.hk.bank.locator.core.preferences.impl

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
class AppPreferencesImplTest {
    private val context = InstrumentationRegistry.getInstrumentation().context
    private val testScope = TestScope(StandardTestDispatcher())
    private val testDataStore: DataStore<Preferences> =
        PreferenceDataStoreFactory.create(
            scope = testScope,
            produceFile = { context.preferencesDataStoreFile("test_datastore") },
        )

    private val appPreferences = AppPreferencesImpl(dataStore = testDataStore)

    @Test
    fun test_initial_locale_value() =
        testScope.runTest {
            appPreferences.getLocale().test {
                awaitItem() shouldBe null
                cancelAndIgnoreRemainingEvents()
            }
        }

    @Test
    fun testLocale() =
        testScope.runTest {
            appPreferences.setLocale("zh-HK")
            appPreferences.getLocale().test {
                awaitItem() shouldBe "zh-HK"
                cancelAndIgnoreRemainingEvents()
            }

            testDataStore.edit { it.clear() }
        }
}
