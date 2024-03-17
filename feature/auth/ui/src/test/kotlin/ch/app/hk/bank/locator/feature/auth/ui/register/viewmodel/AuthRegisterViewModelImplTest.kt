package ch.app.hk.bank.locator.feature.auth.ui.register.viewmodel

import app.cash.turbine.test
import ch.app.hk.bank.locator.core.design.ui.ScreenState
import ch.app.hk.bank.locator.feature.auth.data.repo.model.AuthResult
import ch.app.hk.bank.locator.feature.auth.data.repo.repository.AuthRepository
import ch.app.hk.bank.locator.feature.auth.ui.register.state.AuthRegisterUiState
import ch.app.hk.bank.locator.testing.extension.MainDispatcherExtension
import io.kotest.matchers.equality.shouldBeEqualToComparingFields
import io.kotest.matchers.shouldBe
import io.mockk.coEvery
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
@DisplayName("AuthRegisterViewModelImpl unit tests")
class AuthRegisterViewModelImplTest {
    private val authRepository = mockk<AuthRepository>()

    private val authRegisterViewModel = AuthRegisterViewModelImpl(authRepository = authRepository)

    @ParameterizedTest(
        name =
            "test anonymousLogin(), when authRepository.anonymousLogin() returns {0}, " +
                "then AuthRegisterViewModelImpl.uiState should be {1}",
    )
    @ArgumentsSource(AnonymousLoginArgumentProvider::class)
    fun testAnonymousLogin(
        mockAuthRepositoryAnonymousLoginValue: AuthResult,
        expectedResult: ScreenState<AuthRegisterUiState>,
    ) = runTest {
        coEvery { authRepository.anonymousLogin() } returns mockAuthRepositoryAnonymousLoginValue

        authRegisterViewModel.anonymousLogin()

        authRegisterViewModel.uiState.test {
            awaitItem() shouldBe ScreenState.Empty
            awaitItem() shouldBe ScreenState.Loading
            awaitItem() shouldBeEqualToComparingFields expectedResult
            cancelAndIgnoreRemainingEvents()
        }
    }

    private class AnonymousLoginArgumentProvider : ArgumentsProvider {
        override fun provideArguments(extensionContext: ExtensionContext): Stream<Arguments> {
            return Stream.of(
                Arguments.arguments(
                    AuthResult.Authorized,
                    ScreenState.Success(AuthRegisterUiState.Authorized),
                ),
                Arguments.arguments(
                    AuthResult.Failed(""),
                    ScreenState.Error(AuthRegisterUiState.Failed),
                ),
            )
        }
    }
}
