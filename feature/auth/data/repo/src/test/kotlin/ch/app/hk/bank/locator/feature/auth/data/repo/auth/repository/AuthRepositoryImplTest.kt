package ch.app.hk.bank.locator.feature.auth.data.repo.auth.repository

import ch.app.hk.bank.locator.core.preferences.api.AppPreferencesRepository
import io.kotest.matchers.shouldBe
import io.mockk.Runs
import io.mockk.coEvery
import io.mockk.every
import io.mockk.just
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Test

class AuthRepositoryImplTest {
    private val appPreferencesRepository = mockk<AppPreferencesRepository>()

    private val authRepositoryImpl = AuthRepositoryImpl(appPreferencesRepository)

    @Test
    fun `test isAuthInitialized`() =
        runTest(StandardTestDispatcher()) {
            every { appPreferencesRepository.getBoolean(key = any()) } returns
                flowOf(true)

            authRepositoryImpl.isAuthInitialized() shouldBe true

            verify { appPreferencesRepository.getBoolean("pref_key_is_auth_initialized") }
        }

    @Test
    fun `test setAuthInitialized`() =
        runTest {
            coEvery { appPreferencesRepository.setBoolean(key = any(), value = any()) } just Runs

            authRepositoryImpl.setAuthInitialized()

            coEvery {
                appPreferencesRepository.setBoolean(
                    key = "pref_key_is_auth_initialized",
                    value = true,
                )
            }
        }
}
