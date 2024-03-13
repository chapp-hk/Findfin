package ch.app.hk.bank.locator.feature.onboarding.ui.permission.viewmodel

import app.cash.turbine.test
import ch.app.hk.bank.locator.core.preferences.api.AppPreferencesRepository
import ch.app.hk.bank.locator.core.ui.ScreenState
import ch.app.hk.bank.locator.testing.extension.MainDispatcherExtension
import io.kotest.matchers.shouldBe
import io.mockk.Runs
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.just
import io.mockk.mockk
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

@ExtendWith(MainDispatcherExtension::class)
@DisplayName("PermissionViewModelImpl unit tests")
class PermissionViewModelImplTest {
    private val appPreferencesRepository = mockk<AppPreferencesRepository>(relaxed = true)

    @Test
    @DisplayName(
        "When invoke completeOnboarding(), " +
            "value of uiState should be ScreenState.Success(true)",
    )
    fun `test uiState value`() =
        runTest {
            coEvery { appPreferencesRepository.setBoolean(any(), any()) } just Runs

            val permissionViewModelImpl = createPermissionViewModel()

            permissionViewModelImpl.completeOnboarding()

            permissionViewModelImpl.uiState.test {
                awaitItem() shouldBe ScreenState.Empty
                awaitItem() shouldBe ScreenState.Success(true)
                cancelAndIgnoreRemainingEvents()
            }
        }

    @Test
    @DisplayName(
        "When invoke completeOnboarding(), " +
            "should invoke appPreferencesRepository.setBoolean()",
    )
    fun `verify preference set`() =
        runTest {
            coEvery { appPreferencesRepository.setBoolean(any(), any()) } just Runs

            val permissionViewModelImpl = createPermissionViewModel()

            permissionViewModelImpl.completeOnboarding()
            advanceUntilIdle()

            coVerify { appPreferencesRepository.setBoolean("pref_key_is_app_initialized", true) }
        }

    private fun createPermissionViewModel() = PermissionViewModelImpl(appPreferencesRepository)
}
