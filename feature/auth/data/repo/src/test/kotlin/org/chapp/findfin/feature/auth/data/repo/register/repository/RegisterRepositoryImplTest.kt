package org.chapp.findfin.feature.auth.data.repo.register.repository

import io.kotest.matchers.shouldBe
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.runTest
import org.chapp.findfin.feature.auth.data.repo.register.model.RegisterResult
import org.chapp.findfin.feature.auth.data.repo.register.remote.datasource.RegisterRemoteDataSource
import org.chapp.findfin.feature.auth.data.repo.register.remote.response.RegisterResponse
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.extension.ExtensionContext
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.ArgumentsProvider
import org.junit.jupiter.params.provider.ArgumentsSource
import org.junit.jupiter.params.support.ParameterDeclarations
import java.util.stream.Stream

@DisplayName("RegisterRepositoryImpl unit tests")
class RegisterRepositoryImplTest {
    private val registerRemoteDataSource = mockk<RegisterRemoteDataSource>()

    private val registerRepository = RegisterRepositoryImpl(registerRemoteDataSource = registerRemoteDataSource)

    @ParameterizedTest(
        name =
            "When registerRemoteDataSource.emailPasswordRegister() returns {0}, " +
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

        registerRepository.emailPasswordRegister(
            email = "name@test.com",
            password = "some-password",
        ) shouldBe expectedResult
    }

    private class EmailPasswordRegisterProvider : ArgumentsProvider {
        override fun provideArguments(
            parameterDeclarations: ParameterDeclarations,
            context: ExtensionContext,
        ): Stream<Arguments> =
            Stream.of(
                Arguments.arguments(
                    RegisterResponse.Success,
                    RegisterResult.Authorized,
                ),
                Arguments.arguments(
                    RegisterResponse.Error.Unknown,
                    RegisterResult.Error.Unknown,
                ),
                Arguments.arguments(
                    RegisterResponse.Error.InvalidEmail,
                    RegisterResult.Error.InvalidEmail,
                ),
                Arguments.arguments(
                    RegisterResponse.Error.WeakPassword,
                    RegisterResult.Error.WeakPassword,
                ),
                Arguments.arguments(
                    RegisterResponse.Error.UserCollision,
                    RegisterResult.Error.EmailAlreadyInUse,
                ),
            )
    }
}
