package ch.app.hk.bank.locator.feature.auth.ui.login.viewmodel

import app.cash.turbine.test
import ch.app.hk.bank.locator.core.design.ui.ScreenState
import ch.app.hk.bank.locator.feature.auth.data.repo.login.model.LoginResult
import ch.app.hk.bank.locator.feature.auth.data.repo.login.repository.LoginRepository
import ch.app.hk.bank.locator.feature.auth.ui.login.state.LoginError
import ch.app.hk.bank.locator.feature.auth.ui.login.state.LoginUiState
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
@DisplayName("AuthLoginViewModelImpl unit tests")
class AuthLoginViewModelImplTest {
    private val loginRepositoryImpl = mockk<LoginRepository>()

    private val authLoginViewModel = AuthLoginViewModelImpl(loginRepository = loginRepositoryImpl)

    @ParameterizedTest(
        name =
            "when loginRepositoryImpl.emailPasswordLogin() returns {0}, " +
                "then AuthLoginViewModelImpl.uiState should be {1}",
    )
    @ArgumentsSource(EmailPasswordLoginArgumentProvider::class)
    fun `test emailPasswordLogin`(
        mockAuthRepositoryAnonymousLoginValue: LoginResult,
        expectedResult: ScreenState<LoginUiState, LoginUiState.Error>,
    ) = runTest {
        coEvery {
            loginRepositoryImpl.emailPasswordLogin(
                email = any(),
                password = any(),
            )
        } returns mockAuthRepositoryAnonymousLoginValue

        authLoginViewModel.emailPasswordLogin(
            email = "name@test.com",
            password = "123456",
        )

        authLoginViewModel.uiState.test {
            awaitItem() shouldBe ScreenState.Empty
            awaitItem() shouldBe ScreenState.Loading
            awaitItem() shouldBeEqualToComparingFields expectedResult
            cancelAndIgnoreRemainingEvents()
        }
    }

    private class EmailPasswordLoginArgumentProvider : ArgumentsProvider {
        override fun provideArguments(extensionContext: ExtensionContext): Stream<Arguments> {
            return Stream.of(
                Arguments.arguments(
                    LoginResult.Success,
                    ScreenState.Success<LoginUiState, LoginUiState.Error>(LoginUiState.Authorized),
                ),
                Arguments.arguments(
                    LoginResult.Error.Unknown,
                    ScreenState.Error<LoginUiState, LoginUiState.Error>(LoginUiState.Error(LoginError.UNKNOWN)),
                ),
                Arguments.arguments(
                    LoginResult.Error.AccountDisabled,
                    ScreenState.Error<LoginUiState, LoginUiState.Error>(LoginUiState.Error(LoginError.ACCOUNT_DISABLED)),
                ),
                Arguments.arguments(
                    LoginResult.Error.InvalidCredential,
                    ScreenState.Error<LoginUiState, LoginUiState.Error>(LoginUiState.Error(LoginError.INVALID_CREDENTIAL)),
                ),
                Arguments.arguments(
                    LoginResult.Error.TooManyRequest,
                    ScreenState.Error<LoginUiState, LoginUiState.Error>(LoginUiState.Error(LoginError.TOO_MANY_REQUEST)),
                ),
            )
        }
    }
}
