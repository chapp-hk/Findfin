package ch.app.hk.bank.locator.feature.auth.navigation.viewmodel

import app.cash.turbine.test
import ch.app.hk.bank.locator.core.preferences.api.AppPreferencesRepository
import ch.app.hk.bank.locator.testing.extension.MainDispatcherExtension
import io.kotest.matchers.shouldBe
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

@ExtendWith(MainDispatcherExtension::class)
@DisplayName("AuthNavViewModel unit tests")
class AuthNavViewModelTest {
    private val appPreferencesRepository = mockk<AppPreferencesRepository>(relaxed = true)

    @Test
    fun `navState should emit IsInitialized when preference is true`() =
        runTest {
            every { appPreferencesRepository.getBoolean(any()) } returns flowOf(true)

            val viewModel = createAuthNavViewModel()

            verify {
                appPreferencesRepository.getBoolean("auth_pref_key_is_initialized_auth")
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
            every { appPreferencesRepository.getBoolean(any()) } returns flowOf(false)

            val viewModel = createAuthNavViewModel()

            verify {
                appPreferencesRepository.getBoolean("auth_pref_key_is_initialized_auth")
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
                appPreferencesRepository.setBoolean("auth_pref_key_is_initialized_auth", true)
            }
        }

    private fun createAuthNavViewModel() = AuthNavViewModel(appPreferencesRepository)
}
