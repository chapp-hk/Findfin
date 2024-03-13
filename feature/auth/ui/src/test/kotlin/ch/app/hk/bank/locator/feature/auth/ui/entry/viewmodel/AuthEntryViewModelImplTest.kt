package ch.app.hk.bank.locator.feature.auth.ui.entry.viewmodel

import app.cash.turbine.test
import ch.app.hk.bank.locator.core.design.ui.ScreenState
import ch.app.hk.bank.locator.feature.auth.data.repo.repository.AuthRepository
import ch.app.hk.bank.locator.feature.auth.ui.entry.state.AuthEntryUiState
import ch.app.hk.bank.locator.testing.extension.MainDispatcherExtension
import io.kotest.matchers.shouldBe
import io.mockk.every
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
    private val authRepository = mockk<AuthRepository>()

    @ParameterizedTest(
        name =
            "test uiState, when authRepository.isAuthorized() returns {0}, " +
                "then AuthEntryViewModelImpl.uiState should be {1}",
    )
    @ArgumentsSource(UiStateArgumentProvider::class)
    fun testUiState(
        mockIsAuthorizedValue: Boolean,
        expectedResult: AuthEntryUiState,
    ) = runTest {
        every { authRepository.isAuthorized() } returns mockIsAuthorizedValue

        val authEntryViewModel = createAuthEntryViewModel()

        authEntryViewModel.uiState.test {
            awaitItem() shouldBe ScreenState.Empty
            awaitItem() shouldBe ScreenState.Success(expectedResult)
            cancelAndIgnoreRemainingEvents()
        }
    }

    private class UiStateArgumentProvider : ArgumentsProvider {
        override fun provideArguments(p0: ExtensionContext?): Stream<out Arguments> {
            return Stream.of(
                Arguments.arguments(false, AuthEntryUiState.StartAuth),
                Arguments.arguments(true, AuthEntryUiState.Authorized),
            )
        }
    }

    private fun createAuthEntryViewModel() = AuthEntryViewModelImpl(authRepository = authRepository)
}
