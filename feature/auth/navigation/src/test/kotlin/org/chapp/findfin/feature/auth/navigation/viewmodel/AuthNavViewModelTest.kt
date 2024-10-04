package org.chapp.findfin.feature.auth.navigation.viewmodel

import app.cash.turbine.test
import io.kotest.matchers.shouldBe
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.chapp.findfin.core.preferences.storage.AppPreferencesManager
import org.chapp.findfin.testing.extension.MainDispatcherExtension
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

@ExtendWith(MainDispatcherExtension::class)
@DisplayName("AuthNavViewModel unit tests")
class AuthNavViewModelTest {
    private val appPreferencesManager = mockk<AppPreferencesManager>(relaxed = true)

    @Test
    fun `navState should emit IsInitialized when preference is true`() =
        runTest {
            every { appPreferencesManager.getBoolean(any()) } returns flowOf(true)

            val viewModel = createAuthNavViewModel()

            verify {
                appPreferencesManager.getBoolean("auth_pref_key_is_initialized_auth")
            }

            viewModel.navState.test {
                awaitItem() shouldBe AuthNavState.Loading
                awaitItem() shouldBe AuthNavState.IsInitialized
                cancelAndIgnoreRemainingEvents()
            }
        }

    @Test
    fun `navState should emit NotInitialized when preference is false`() =
        runTest {
            every { appPreferencesManager.getBoolean(any()) } returns flowOf(false)

            val viewModel = createAuthNavViewModel()

            verify {
                appPreferencesManager.getBoolean("auth_pref_key_is_initialized_auth")
            }

            viewModel.navState.test {
                awaitItem() shouldBe AuthNavState.Loading
                awaitItem() shouldBe AuthNavState.NotInitialized
                cancelAndIgnoreRemainingEvents()
            }
        }

    @Test
    fun `setAuthInitialized should update preference to true`() =
        runTest {
            val viewModel = createAuthNavViewModel()

            viewModel.setAuthInitialized()

            delay(500)
            coVerify {
                appPreferencesManager.setBoolean("auth_pref_key_is_initialized_auth", true)
            }
        }

    private fun createAuthNavViewModel() = AuthNavViewModel(appPreferencesManager)
}
