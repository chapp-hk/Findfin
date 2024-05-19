package ch.app.hk.bank.locator.feature.auth.ui.entry.viewmodel

import androidx.lifecycle.SavedStateHandle
import app.cash.turbine.test
import ch.app.hk.bank.locator.core.design.ui.ScreenState
import ch.app.hk.bank.locator.feature.auth.data.repo.auth.repository.AuthRepository
import ch.app.hk.bank.locator.feature.auth.ui.entry.state.AuthEntryUiState
import ch.app.hk.bank.locator.testing.extension.MainDispatcherExtension
import io.kotest.matchers.shouldBe
import io.mockk.Runs
import io.mockk.coEvery
import io.mockk.just
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.extension.ExtendWith
import org.junit.jupiter.api.extension.ExtensionContext
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.ArgumentsProvider
import org.junit.jupiter.params.provider.ArgumentsSource
import java.util.stream.Stream

@ExtendWith(MainDispatcherExtension::class)
@DisplayName("AuthEntryViewModelImpl unit tests")
class AuthEntryViewModelImplTest {
    private val savedStateHandle = SavedStateHandle()
    private val authRepository = mockk<AuthRepository>()

    @ParameterizedTest(
        name =
            "test uiState, when authRepository.isAuthInitialized() returns {0}, " +
                "then AuthEntryViewModelImpl.uiState should be {1}",
    )
    @ArgumentsSource(UiStateArgumentProvider::class)
    fun testUiState(
        mockShouldCheckIsInitValue: Boolean,
        mockIsAuthInitializedValue: Boolean,
        expectedResult: AuthEntryUiState,
    ) = runTest {
        savedStateHandle["shouldCheckIsInit"] = mockShouldCheckIsInitValue
        coEvery { authRepository.isAuthInitialized() } returns mockIsAuthInitializedValue
        coEvery { authRepository.setAuthInitialized() } just Runs

        val authEntryViewModel = createAuthEntryViewModel()

        authEntryViewModel.uiState.test {
            awaitItem() shouldBe ScreenState.Empty
            awaitItem() shouldBe ScreenState.Success(expectedResult)
            cancelAndIgnoreRemainingEvents()
        }
    }

    private class UiStateArgumentProvider : ArgumentsProvider {
        /**
         * argument 0 - mockShouldCheckIsInitValue: Boolean,
         * argument 1 - mockIsAuthInitializedValue: Boolean,
         * argument 2 - expectedResult: AuthEntryUiState,
         */
        override fun provideArguments(context: ExtensionContext): Stream<out Arguments> {
            return Stream.of(
                Arguments.arguments(
                    true,
                    false,
                    AuthEntryUiState.StartAuth,
                ),
                Arguments.arguments(
                    true,
                    true,
                    AuthEntryUiState.AuthInitialized,
                ),
                Arguments.arguments(
                    false,
                    false,
                    AuthEntryUiState.StartAuth,
                ),
            )
        }
    }

    private fun createAuthEntryViewModel() =
        AuthEntryViewModelImpl(
            savedStateHandle = savedStateHandle,
            authRepository = authRepository,
        )
}
