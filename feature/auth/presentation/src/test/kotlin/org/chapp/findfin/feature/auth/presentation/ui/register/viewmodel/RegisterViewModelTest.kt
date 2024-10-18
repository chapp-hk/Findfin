package org.chapp.findfin.feature.auth.presentation.ui.register.viewmodel

import app.cash.turbine.test
import io.kotest.matchers.equality.shouldBeEqualToComparingFields
import io.kotest.matchers.shouldBe
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.chapp.findfin.feature.auth.data.repo.register.model.RegisterResult
import org.chapp.findfin.feature.auth.data.repo.register.repository.RegisterRepository
import org.chapp.findfin.feature.auth.presentation.ui.register.state.RegisterError
import org.chapp.findfin.feature.auth.presentation.ui.register.state.RegisterUiState
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
class RegisterViewModelTest {
    private val registerRepository = mockk<RegisterRepository>()

    private val registerViewModel = RegisterViewModel(registerRepository = registerRepository)

    @ParameterizedTest(
        name =
            "when registerRepository.emailPasswordRegister() returns {0}, " +
                "then AuthRegisterViewModel.uiState should be {1}",
    )
    @ArgumentsSource(EmailPasswordRegisterArgumentProvider::class)
    fun `test emailPasswordRegister`(
        mockAuthRepositoryAnonymousLoginValue: RegisterResult,
        expectedResult: RegisterUiState,
    ) = runTest {
        coEvery {
            registerRepository.emailPasswordRegister(
                email = any(),
                password = any(),
            )
        } returns mockAuthRepositoryAnonymousLoginValue

        registerViewModel.emailPasswordRegister(
            email = "name@test.com",
            password = "123456",
        )

        registerViewModel.uiState.test {
            awaitItem() shouldBe RegisterUiState.None
            awaitItem() shouldBe RegisterUiState.Loading
            awaitItem() shouldBeEqualToComparingFields expectedResult
            cancelAndIgnoreRemainingEvents()
        }
    }

    private class EmailPasswordRegisterArgumentProvider : ArgumentsProvider {
        override fun provideArguments(extensionContext: ExtensionContext): Stream<Arguments> {
            return Stream.of(
                Arguments.arguments(
                    RegisterResult.Authorized,
                    RegisterUiState.Authorized,
                ),
                Arguments.arguments(
                    RegisterResult.Error.Unknown,
                    RegisterUiState.Error(RegisterError.UNKNOWN),
                ),
                Arguments.arguments(
                    RegisterResult.Error.EmailAlreadyInUse,
                    RegisterUiState.Error(RegisterError.EMAIL_ALREADY_IN_USE),
                ),
                Arguments.arguments(
                    RegisterResult.Error.WeakPassword,
                    RegisterUiState.Error(RegisterError.WEAK_PASSWORD),
                ),
                Arguments.arguments(
                    RegisterResult.Error.InvalidEmail,
                    RegisterUiState.Error(RegisterError.INVALID_EMAIL),
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

            registerViewModel.emailPasswordRegister(email = "test@test.com", password = "1111")
            registerViewModel.resetUiState()

            registerViewModel.uiState.test {
                awaitItem() shouldBe RegisterUiState.None
                awaitItem() shouldBe RegisterUiState.Loading
                awaitItem() shouldBeEqualToComparingFields RegisterUiState.Authorized
                awaitItem() shouldBe RegisterUiState.None
                cancelAndIgnoreRemainingEvents()
            }
        }
}
