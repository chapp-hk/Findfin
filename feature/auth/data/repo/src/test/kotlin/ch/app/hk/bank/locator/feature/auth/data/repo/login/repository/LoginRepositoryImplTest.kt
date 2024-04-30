package ch.app.hk.bank.locator.feature.auth.data.repo.login.repository

import ch.app.hk.bank.locator.feature.auth.data.remote.login.datasource.LoginRemoteDataSource
import ch.app.hk.bank.locator.feature.auth.data.remote.login.response.LoginResponse
import ch.app.hk.bank.locator.feature.auth.data.repo.login.model.LoginResult
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

@DisplayName("LoginRepositoryImpl unit tests")
class LoginRepositoryImplTest {
    private val loginRemoteDataSource = mockk<LoginRemoteDataSource>()

    private val loginRepository = LoginRepositoryImpl(loginRemoteDataSource = loginRemoteDataSource)

    @ParameterizedTest(
        name =
            "When loginRemoteDataSource.emailPasswordLogin() returns {0}, " +
                "then emailPasswordRegister() should return {1}",
    )
    @ArgumentsSource(EmailPasswordLoginProvider::class)
    fun testEmailPasswordRegister(
        mockRemoteDataSourceResponse: LoginResponse,
        expectedResult: LoginResult,
    ) = runTest(StandardTestDispatcher()) {
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
        override fun provideArguments(context: ExtensionContext): Stream<Arguments> =
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
