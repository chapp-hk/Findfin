package ch.app.hk.bank.locator.feature.auth.ui.register.viewmodel

import app.cash.turbine.test
import ch.app.hk.bank.locator.core.design.ui.ScreenState
import ch.app.hk.bank.locator.feature.auth.data.repo.register.model.RegisterResult
import ch.app.hk.bank.locator.feature.auth.data.repo.register.repository.RegisterRepository
import ch.app.hk.bank.locator.feature.auth.ui.register.state.AuthRegisterError
import ch.app.hk.bank.locator.feature.auth.ui.register.state.AuthRegisterUiState
import ch.app.hk.bank.locator.testing.extension.MainDispatcherExtension
import io.kotest.matchers.equality.shouldBeEqualToComparingFields
import io.kotest.matchers.shouldBe
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
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
@DisplayName("AuthRegisterViewModelImpl unit tests")
class AuthRegisterViewModelImplTest {
    private val registerRepository = mockk<RegisterRepository>()

    private val authRegisterViewModel = AuthRegisterViewModelImpl(registerRepository = registerRepository)

    @ParameterizedTest(
        name =
            "when registerRepository.emailPasswordRegister() returns {0}, " +
                "then AuthRegisterViewModelImpl.uiState should be {1}",
    )
    @ArgumentsSource(EmailPasswordRegisterArgumentProvider::class)
    fun `test emailPasswordRegister`(
        mockAuthRepositoryAnonymousLoginValue: RegisterResult,
        expectedResult: ScreenState<AuthRegisterUiState, AuthRegisterUiState.Error>,
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
            awaitItem() shouldBe ScreenState.Empty
            awaitItem() shouldBe ScreenState.Loading
            awaitItem() shouldBeEqualToComparingFields expectedResult
            cancelAndIgnoreRemainingEvents()
        }
    }

    private class EmailPasswordRegisterArgumentProvider : ArgumentsProvider {
        override fun provideArguments(extensionContext: ExtensionContext): Stream<Arguments> {
            return Stream.of(
                Arguments.arguments(
                    RegisterResult.Authorized,
                    ScreenState.Success<AuthRegisterUiState, AuthRegisterUiState.Error>(AuthRegisterUiState.Authorized),
                ),
                Arguments.arguments(
                    RegisterResult.Error.Unknown,
                    ScreenState.Error<AuthRegisterUiState, AuthRegisterUiState.Error>(AuthRegisterUiState.Error(AuthRegisterError.UNKNOWN)),
                ),
                Arguments.arguments(
                    RegisterResult.Error.Register.EmailAlreadyInUse,
                    ScreenState.Error<AuthRegisterUiState, AuthRegisterUiState.Error>(
                        AuthRegisterUiState.Error(AuthRegisterError.EMAIL_ALREADY_IN_USE),
                    ),
                ),
                Arguments.arguments(
                    RegisterResult.Error.Register.WeakPassword,
                    ScreenState.Error<AuthRegisterUiState, AuthRegisterUiState.Error>(
                        AuthRegisterUiState.Error(AuthRegisterError.WEAK_PASSWORD),
                    ),
                ),
                Arguments.arguments(
                    RegisterResult.Error.Register.InvalidEmail,
                    ScreenState.Error<AuthRegisterUiState, AuthRegisterUiState.Error>(
                        AuthRegisterUiState.Error(AuthRegisterError.INVALID_EMAIL),
                    ),
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
                awaitItem() shouldBe ScreenState.Empty
                awaitItem() shouldBe ScreenState.Loading
                awaitItem() shouldBeEqualToComparingFields ScreenState.Success(AuthRegisterUiState.Authorized)
                awaitItem() shouldBe ScreenState.Empty
                cancelAndIgnoreRemainingEvents()
            }
        }
}
