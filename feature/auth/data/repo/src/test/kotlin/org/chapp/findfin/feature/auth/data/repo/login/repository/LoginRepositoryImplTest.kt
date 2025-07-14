package org.chapp.findfin.feature.auth.data.repo.login.repository

import io.kotest.matchers.shouldBe
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.runTest
import org.chapp.findfin.feature.auth.data.repo.login.model.LoginResult
import org.chapp.findfin.feature.auth.data.repo.login.remote.datasource.LoginRemoteDataSource
import org.chapp.findfin.feature.auth.data.repo.login.remote.response.LoginResponse
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.extension.ExtensionContext
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.ArgumentsProvider
import org.junit.jupiter.params.provider.ArgumentsSource
import org.junit.jupiter.params.support.ParameterDeclarations
import java.util.stream.Stream

@DisplayName("LoginRepositoryImpl unit tests")
class LoginRepositoryImplTest {
    private val testDispatcher = StandardTestDispatcher()
    private val loginRemoteDataSource = mockk<LoginRemoteDataSource>()

    private val loginRepository =
        LoginRepositoryImpl(
            ioDispatcher = testDispatcher,
            loginRemoteDataSource = loginRemoteDataSource,
        )

    @ParameterizedTest(
        name =
            "When loginRemoteDataSource.emailPasswordLogin() returns {0}, " +
                "then emailPasswordRegister() should return {1}",
    )
    @ArgumentsSource(EmailPasswordLoginProvider::class)
    fun testEmailPasswordRegister(
        mockRemoteDataSourceResponse: LoginResponse,
        expectedResult: LoginResult,
    ) = runTest(context = testDispatcher) {
        coEvery {
            loginRemoteDataSource.emailPasswordLogin(
                email = any(),
                password = any(),
            )
        } returns mockRemoteDataSourceResponse

        loginRepository.emailPasswordLogin(
            email = "name@test.com",
            password = "some-password",
        ) shouldBe expectedResult
    }

    private class EmailPasswordLoginProvider : ArgumentsProvider {
        override fun provideArguments(
            parameterDeclarations: ParameterDeclarations,
            context: ExtensionContext,
        ): Stream<Arguments> =
            Stream.of(
                Arguments.arguments(
                    LoginResponse.Error.AccountDisabled,
                    LoginResult.Error.AccountDisabled,
                ),
                Arguments.arguments(
                    LoginResponse.Error.InvalidCredential,
                    LoginResult.Error.InvalidCredential,
                ),
                Arguments.arguments(
                    LoginResponse.Error.TooManyRequest,
                    LoginResult.Error.TooManyRequest,
                ),
                Arguments.arguments(
                    LoginResponse.Error.Unknown,
                    LoginResult.Error.Unknown,
                ),
                Arguments.arguments(
                    LoginResponse.Success,
                    LoginResult.Success,
                ),
            )
    }
}
