package ch.app.hk.bank.locator.feature.auth.data.repo.repository

import ch.app.hk.bank.locator.feature.auth.data.remote.datasource.AuthRemoteDataSource
import ch.app.hk.bank.locator.feature.auth.data.remote.response.AuthResponse
import ch.app.hk.bank.locator.feature.auth.data.repo.model.AuthErrorCode
import ch.app.hk.bank.locator.feature.auth.data.repo.model.AuthResult
import io.kotest.matchers.shouldBe
import io.mockk.coEvery
import io.mockk.every
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
class AuthRepositoryImplTest {
    private val authRemoteDataSource = mockk<AuthRemoteDataSource>()

    private val authRepository = AuthRepositoryImpl(authRemoteDataSource = authRemoteDataSource)

    @ParameterizedTest(
        name =
            "When authRemoteDataSource.isAuthorized() returns {0}, " +
                "then isAuthorized() should return {1}",
    )
    @ArgumentsSource(IsAuthorizedProvider::class)
    fun testIsAuthorized(
        mockIsAuthorized: Boolean,
        expectedResult: Boolean,
    ) {
        every { authRemoteDataSource.isAuthorized() } returns mockIsAuthorized

        authRepository.isAuthorized() shouldBe expectedResult
    }

    private class IsAuthorizedProvider : ArgumentsProvider {
        override fun provideArguments(context: ExtensionContext): Stream<Arguments> =
            Stream.of(
                Arguments.arguments(true, true),
                Arguments.arguments(false, false),
            )
    }

    @ParameterizedTest(
        name =
            "When authRemoteDataSource.anonymousLogin() returns {0}, " +
                "then anonymousLogin() should return {1}",
    )
    @ArgumentsSource(AnonymousLoginProvider::class)
    fun testAnonymousLogin(
        mockRemoteDataSourceResponse: AuthResponse,
        expectedResult: AuthResult,
    ) = runTest(StandardTestDispatcher()) {
        coEvery { authRemoteDataSource.anonymousLogin() } returns mockRemoteDataSourceResponse

        authRepository.anonymousLogin() shouldBe expectedResult
    }

    private class AnonymousLoginProvider : ArgumentsProvider {
        override fun provideArguments(context: ExtensionContext): Stream<Arguments> =
            Stream.of(
                Arguments.arguments(
                    AuthResponse.Success(isAnonymous = false),
                    AuthResult.Authorized,
                ),
                Arguments.arguments(
                    AuthResponse.Error(code = "", message = ""),
                    AuthResult.Error.Unknown,
                ),
            )
    }

    @ParameterizedTest(
        name =
            "When authRemoteDataSource.emailPasswordRegister() returns {0}, " +
                "then emailPasswordRegister() should return {1}",
    )
    @ArgumentsSource(EmailPasswordRegisterProvider::class)
    fun testEmailPasswordRegister(
        mockRemoteDataSourceResponse: AuthResponse,
        expectedResult: AuthResult,
    ) = runTest(StandardTestDispatcher()) {
        coEvery {
            authRemoteDataSource.emailPasswordRegister(
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
                    AuthResponse.Success(isAnonymous = false),
                    AuthResult.Authorized,
                ),
                Arguments.arguments(
                    AuthResponse.Error(
                        code = AuthErrorCode.ERROR_UNKNOWN.name,
                        message = "",
                    ),
                    AuthResult.Error.Unknown,
                ),
                Arguments.arguments(
                    AuthResponse.Error(
                        code = AuthErrorCode.ERROR_INVALID_EMAIL.name,
                        message = "",
                    ),
                    AuthResult.Error.Register.InvalidEmail,
                ),
                Arguments.arguments(
                    AuthResponse.Error(
                        code = AuthErrorCode.ERROR_WEAK_PASSWORD.name,
                        message = "",
                    ),
                    AuthResult.Error.Register.WeakPassword,
                ),
                Arguments.arguments(
                    AuthResponse.Error(
                        code = AuthErrorCode.ERROR_EMAIL_ALREADY_IN_USE.name,
                        message = "",
                    ),
                    AuthResult.Error.Register.EmailAlreadyInUse,
                ),
            )
    }
}
