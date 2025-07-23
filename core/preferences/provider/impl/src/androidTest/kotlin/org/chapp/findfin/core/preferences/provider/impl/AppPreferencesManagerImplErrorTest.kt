package org.chapp.findfin.core.preferences.provider.impl

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStoreFile
import androidx.test.filters.SmallTest
import androidx.test.platform.app.InstrumentationRegistry
import app.cash.turbine.test
import io.kotest.matchers.shouldBe
import io.mockk.every
import io.mockk.spyk
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.runTest
import org.junit.Test

@SmallTest
class AppPreferencesManagerImplErrorTest {
    private val context = InstrumentationRegistry.getInstrumentation().context
    private val testScope = TestScope(StandardTestDispatcher())
    private val testDataStore: DataStore<Preferences> =
        spyk(
            PreferenceDataStoreFactory.create(
                scope = testScope.backgroundScope,
                produceFile = { context.preferencesDataStoreFile("test_datastore") },
            ),
        )

    private val appPreferences = AppPreferencesManagerImpl(dataStore = testDataStore)

    @Test
    fun test_getBoolean_cancellation_exception_handling() {
        testScope.runTest {
            every { testDataStore.data } returns
                flow { throw CancellationException("Test cancellation exception") }

            appPreferences
                .getBoolean(
                    key = "test_boolean",
                    defaultValue = false,
                )
                .test {
                    awaitError() shouldBe CancellationException("Test cancellation exception")
                    cancelAndIgnoreRemainingEvents()
                }
        }
    }

    @Test
    fun test_getBoolean_other_exception_handling() {
        testScope.runTest {
            every { testDataStore.data } returns
                flow { throw Exception("Test exception") }

            appPreferences
                .getBoolean(
                    key = "test_boolean",
                    defaultValue = false,
                )
                .test {
                    awaitItem() shouldBe false
                    cancelAndIgnoreRemainingEvents()
                }
        }
    }

    @Test
    fun test_getString_cancellation_exception_handling() {
        testScope.runTest {
            every { testDataStore.data } returns
                flow { throw CancellationException("Test cancellation exception") }

            appPreferences
                .getString(
                    key = "test_string",
                    defaultValue = "",
                )
                .test {
                    awaitError() shouldBe CancellationException("Test cancellation exception")
                    cancelAndIgnoreRemainingEvents()
                }
        }
    }

    @Test
    fun test_getString_other_exception_handling() {
        testScope.runTest {
            every { testDataStore.data } returns
                flow { throw Exception("Test exception") }

            appPreferences
                .getString(
                    key = "test_string",
                    defaultValue = "",
                )
                .test {
                    awaitItem() shouldBe ""
                    cancelAndIgnoreRemainingEvents()
                }
        }
    }
}
