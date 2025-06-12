package org.chapp.findfin.feature.auth.data.remote.firebase.service.login

import com.google.firebase.FirebaseTooManyRequestsException
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthInvalidUserException
import io.kotest.matchers.shouldBe
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.runTest
import org.chapp.findfin.feature.auth.data.repo.login.remote.response.LoginResponse
import org.chapp.findfin.testing.google.play.services.task.mockTaskError
import org.chapp.findfin.testing.google.play.services.task.mockTaskResult
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtensionContext
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.ArgumentsProvider
import org.junit.jupiter.params.provider.ArgumentsSource
import org.junit.jupiter.params.support.ParameterDeclarations
import java.lang.Exception
import java.util.stream.Stream

@DisplayName("LoginService unit tests")
class LoginServiceTest {
    private val testDispatcher = StandardTestDispatcher()
    private val firebaseAuth = mockk<FirebaseAuth>()

    private val loginService =
        LoginService(
            ioDispatcher = testDispatcher,
            firebaseAuth = firebaseAuth,
        )

    @Test
    @DisplayName(
        "When firebaseAuth.signInWithEmailAndPassword() returns success, " +
            "then emailPasswordLogin() should return LoginResponse.Success",
    )
    fun testEmailPasswordLoginSuccess() =
        runTest(testDispatcher) {
            val result =
                mockk<AuthResult>(relaxed = true) {
                    every { user?.isAnonymous } returns false
                }

            every { firebaseAuth.signInWithEmailAndPassword(any(), any()) } returns
                mockTaskResult(result)

            loginService.emailPasswordLogin(
                email = "test@domain.com",
                password = "*****",
            ) shouldBe LoginResponse.Success
        }

    @ParameterizedTest(
        name =
            "When firebaseAuth.signInWithEmailAndPassword() returns {0}, " +
                "then emailPasswordRegister() should return {1}",
    )
    @ArgumentsSource(EmailPasswordLoginProvider::class)
    fun testEmailPasswordLoginError(
        mockError: Exception,
        expectedResult: LoginResponse,
    ) = runTest(testDispatcher) {
        every { firebaseAuth.signInWithEmailAndPassword(any(), any()) } returns
            mockTaskError<AuthResult>(mockError)

        loginService.emailPasswordLogin(
            email = "test@domain.com",
            password = "*****",
        ) shouldBe expectedResult
    }

    private class EmailPasswordLoginProvider : ArgumentsProvider {
        override fun provideArguments(
            parameterDeclarations: ParameterDeclarations,
            context: ExtensionContext,
        ): Stream<Arguments> =
            Stream.of(
                Arguments.arguments(
                    FirebaseAuthInvalidCredentialsException("message", "message"),
                    LoginResponse.Error.InvalidCredential,
                ),
                Arguments.arguments(
                    FirebaseAuthInvalidUserException("message", "message"),
                    LoginResponse.Error.AccountDisabled,
                ),
                Arguments.arguments(
                    FirebaseTooManyRequestsException("message"),
                    LoginResponse.Error.TooManyRequest,
                ),
                Arguments.arguments(
                    Exception(),
                    LoginResponse.Error.Unknown,
                ),
            )
    }
}
