package org.chapp.findfin.feature.auth.presentation.ui.register.viewmodel

import app.cash.turbine.test
import io.kotest.matchers.equality.shouldBeEqualToComparingFields
import io.kotest.matchers.shouldBe
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.chapp.findfin.feature.auth.data.repo.register.model.RegisterResult
import org.chapp.findfin.feature.auth.data.repo.register.repository.RegisterRepository
import org.chapp.findfin.feature.auth.presentation.ui.register.state.AuthRegisterError
import org.chapp.findfin.feature.auth.presentation.ui.register.state.AuthRegisterUiState
import org.chapp.findfin.testing.extension.MainDispatcherExtension
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.junit.jupiter.api.extension.ExtensionContext
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.ArgumentsProvider
import org.junit.jupiter.params.provider.ArgumentsSource
import java.util.stream.Stream

@ExtendWith(MainDispatcherExtension::class)
@DisplayName("AuthRegisterViewModel unit tests")
class AuthRegisterViewModelTest {
    private val registerRepository = mockk<RegisterRepository>()

    private val authRegisterViewModel = AuthRegisterViewModel(registerRepository = registerRepository)

    @ParameterizedTest(
        name =
            "when registerRepository.emailPasswordRegister() returns {0}, " +
                "then AuthRegisterViewModel.uiState should be {1}",
    )
    @ArgumentsSource(EmailPasswordRegisterArgumentProvider::class)
    fun `test emailPasswordRegister`(
        mockAuthRepositoryAnonymousLoginValue: RegisterResult,
        expectedResult: AuthRegisterUiState,
    ) = runTest {
        coEvery {
            registerRepository.emailPasswordRegister(
                email = any(),
                password = any(),
            )
        } returns mockAuthRepositoryAnonymousLoginValue

        authRegisterViewModel.emailPasswordRegister(
            email = "name@test.com",
            password = "123456",
        )

        authRegisterViewModel.uiState.test {
            awaitItem() shouldBe AuthRegisterUiState.None
            awaitItem() shouldBe AuthRegisterUiState.Loading
            awaitItem() shouldBeEqualToComparingFields expectedResult
            cancelAndIgnoreRemainingEvents()
        }
    }

    private class EmailPasswordRegisterArgumentProvider : ArgumentsProvider {
        override fun provideArguments(extensionContext: ExtensionContext): Stream<Arguments> {
            return Stream.of(
                Arguments.arguments(
                    RegisterResult.Authorized,
                    AuthRegisterUiState.Authorized,
                ),
                Arguments.arguments(
                    RegisterResult.Error.Unknown,
                    AuthRegisterUiState.Error(AuthRegisterError.UNKNOWN),
                ),
                Arguments.arguments(
                    RegisterResult.Error.Register.EmailAlreadyInUse,
                    AuthRegisterUiState.Error(AuthRegisterError.EMAIL_ALREADY_IN_USE),
                ),
                Arguments.arguments(
                    RegisterResult.Error.Register.WeakPassword,
                    AuthRegisterUiState.Error(AuthRegisterError.WEAK_PASSWORD),
                ),
                Arguments.arguments(
                    RegisterResult.Error.Register.InvalidEmail,
                    AuthRegisterUiState.Error(AuthRegisterError.INVALID_EMAIL),
                ),
            )
        }
    }

    @Test
    fun `test resetUiState`() =
        runTest {
            coEvery {
                registerRepository.emailPasswordRegister(
                    email = any(),
                    password = any(),
                )
            } returns RegisterResult.Authorized

            authRegisterViewModel.emailPasswordRegister(email = "test@test.com", password = "1111")
            authRegisterViewModel.resetUiState()

            authRegisterViewModel.uiState.test {
                awaitItem() shouldBe AuthRegisterUiState.None
                awaitItem() shouldBe AuthRegisterUiState.Loading
                awaitItem() shouldBeEqualToComparingFields AuthRegisterUiState.Authorized
                awaitItem() shouldBe AuthRegisterUiState.None
                cancelAndIgnoreRemainingEvents()
            }
        }
}
