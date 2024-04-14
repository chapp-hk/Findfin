package ch.app.hk.bank.locator.feature.auth.data.repo.register.repository

import ch.app.hk.bank.locator.feature.auth.data.remote.register.datasource.RegisterRemoteDataSource
import ch.app.hk.bank.locator.feature.auth.data.remote.register.response.RegisterResponse
import ch.app.hk.bank.locator.feature.auth.data.repo.register.model.RegisterErrorCode
import ch.app.hk.bank.locator.feature.auth.data.repo.register.model.RegisterResult
import io.kotest.matchers.shouldBe
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.extension.ExtensionContext
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.ArgumentsProvider
import org.junit.jupiter.params.provider.ArgumentsSource
import java.util.stream.Stream

@DisplayName("AuthRepositoryImpl unit tests")
class RegisterRepositoryImplTest {
    private val registerRemoteDataSource = mockk<RegisterRemoteDataSource>()

    private val authRepository = RegisterRepositoryImpl(registerRemoteDataSource = registerRemoteDataSource)

    @ParameterizedTest(
        name =
            "When authRemoteDataSource.emailPasswordRegister() returns {0}, " +
                "then emailPasswordRegister() should return {1}",
    )
    @ArgumentsSource(EmailPasswordRegisterProvider::class)
    fun testEmailPasswordRegister(
        mockRemoteDataSourceResponse: RegisterResponse,
        expectedResult: RegisterResult,
    ) = runTest(StandardTestDispatcher()) {
        coEvery {
            registerRemoteDataSource.emailPasswordRegister(
                email = any(),
                password = any(),
            )
        } returns mockRemoteDataSourceResponse

        authRepository.emailPasswordRegister(
            email = "name@test.com",
            password = "some-password",
        ) shouldBe expectedResult
    }

    private class EmailPasswordRegisterProvider : ArgumentsProvider {
        override fun provideArguments(context: ExtensionContext): Stream<Arguments> =
            Stream.of(
                Arguments.arguments(
                    RegisterResponse.Success(isAnonymous = false),
                    RegisterResult.Authorized,
                ),
                Arguments.arguments(
                    RegisterResponse.Error(
                        code = RegisterErrorCode.ERROR_UNKNOWN.name,
                        message = "",
                    ),
                    RegisterResult.Error.Unknown,
                ),
                Arguments.arguments(
                    RegisterResponse.Error(
                        code = RegisterErrorCode.ERROR_INVALID_EMAIL.name,
                        message = "",
                    ),
                    RegisterResult.Error.Register.InvalidEmail,
                ),
                Arguments.arguments(
                    RegisterResponse.Error(
                        code = RegisterErrorCode.ERROR_WEAK_PASSWORD.name,
                        message = "",
                    ),
                    RegisterResult.Error.Register.WeakPassword,
                ),
                Arguments.arguments(
                    RegisterResponse.Error(
                        code = RegisterErrorCode.ERROR_EMAIL_ALREADY_IN_USE.name,
                        message = "",
                    ),
                    RegisterResult.Error.Register.EmailAlreadyInUse,
                ),
            )
    }
}
