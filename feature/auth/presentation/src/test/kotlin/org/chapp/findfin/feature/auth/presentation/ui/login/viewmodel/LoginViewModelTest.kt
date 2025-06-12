package org.chapp.findfin.feature.auth.presentation.ui.login.viewmodel

import app.cash.turbine.test
import io.kotest.matchers.equality.shouldBeEqualToComparingFields
import io.kotest.matchers.shouldBe
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.chapp.findfin.feature.auth.data.repo.login.model.LoginResult
import org.chapp.findfin.feature.auth.data.repo.login.repository.LoginRepository
import org.chapp.findfin.feature.auth.presentation.ui.login.state.LoginError
import org.chapp.findfin.feature.auth.presentation.ui.login.state.LoginUiState
import org.chapp.findfin.testing.extension.MainDispatcherExtension
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.extension.ExtendWith
import org.junit.jupiter.api.extension.ExtensionContext
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.ArgumentsProvider
import org.junit.jupiter.params.provider.ArgumentsSource
import org.junit.jupiter.params.support.ParameterDeclarations
import java.util.stream.Stream

@ExtendWith(MainDispatcherExtension::class)
@DisplayName("AuthLoginViewModel unit tests")
internal class LoginViewModelTest {
    private val loginRepositoryImpl = mockk<LoginRepository>()

    private val loginViewModel = LoginViewModel(loginRepository = loginRepositoryImpl)

    @ParameterizedTest(
        name =
            "when loginRepositoryImpl.emailPasswordLogin() returns {0}, " +
                "then AuthLoginViewModel.uiState should be {1}",
    )
    @ArgumentsSource(EmailPasswordLoginArgumentProvider::class)
    fun `test emailPasswordLogin`(
        mockAuthRepositoryAnonymousLoginValue: LoginResult,
        expectedResult: LoginUiState,
    ) = runTest {
        coEvery {
            loginRepositoryImpl.emailPasswordLogin(
                email = any(),
                password = any(),
            )
        } returns mockAuthRepositoryAnonymousLoginValue

        loginViewModel.emailPasswordLogin(
            email = "name@test.com",
            password = "123456",
        )

        loginViewModel.uiState.test {
            awaitItem() shouldBe LoginUiState.None
            awaitItem() shouldBe LoginUiState.Loading
            awaitItem() shouldBeEqualToComparingFields expectedResult
            cancelAndIgnoreRemainingEvents()
        }
    }

    private class EmailPasswordLoginArgumentProvider : ArgumentsProvider {
        override fun provideArguments(
            parameterDeclarations: ParameterDeclarations,
            extensionContext: ExtensionContext,
        ): Stream<Arguments> {
            return Stream.of(
                Arguments.arguments(
                    LoginResult.Success,
                    LoginUiState.Authorized,
                ),
                Arguments.arguments(
                    LoginResult.Error.Unknown,
                    LoginUiState.Error(LoginError.UNKNOWN),
                ),
                Arguments.arguments(
                    LoginResult.Error.AccountDisabled,
                    LoginUiState.Error(LoginError.ACCOUNT_DISABLED),
                ),
                Arguments.arguments(
                    LoginResult.Error.InvalidCredential,
                    LoginUiState.Error(LoginError.INVALID_CREDENTIAL),
                ),
                Arguments.arguments(
                    LoginResult.Error.TooManyRequest,
                    LoginUiState.Error(LoginError.TOO_MANY_REQUEST),
                ),
            )
        }
    }
}
