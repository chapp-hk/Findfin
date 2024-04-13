package ch.app.hk.bank.locator.feature.auth.data.remote.firebase.service.register

import ch.app.hk.bank.locator.feature.auth.data.remote.register.response.RegisterResponse
import ch.app.hk.bank.locator.testing.google.play.services.task.mockTaskError
import ch.app.hk.bank.locator.testing.google.play.services.task.mockTaskResult
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthException
import com.google.firebase.auth.FirebaseUser
import io.kotest.matchers.shouldBe
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtensionContext
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.Arguments.arguments
import org.junit.jupiter.params.provider.ArgumentsProvider
import org.junit.jupiter.params.provider.ArgumentsSource
import java.lang.Exception
import java.util.stream.Stream

@DisplayName("AuthService unit tests")
class RegisterServiceTest {
    private val testDispatcher = StandardTestDispatcher()
    private val firebaseAuth = mockk<FirebaseAuth>()

    private val registerService =
        RegisterService(
            ioDispatcher = testDispatcher,
            firebaseAuth = firebaseAuth,
        )

    @ParameterizedTest(
        name =
            "When firebaseAuth.currentUser returns {0}, " +
                "then isAuthorized() should return {1}",
    )
    @ArgumentsSource(IsAuthorizedProvider::class)
    fun testIsAuthorized(
        mockCurrentUser: FirebaseUser?,
        expectedResult: Boolean,
    ) {
        every { firebaseAuth.currentUser } returns mockCurrentUser

        registerService.isAuthorized() shouldBe expectedResult
    }

    private class IsAuthorizedProvider : ArgumentsProvider {
        override fun provideArguments(context: ExtensionContext): Stream<Arguments> =
            Stream.of(
                arguments(mockk<FirebaseUser>(relaxed = true), true),
                arguments(null, false),
            )
    }

    @Test
    @DisplayName(
        "When firebaseAuth.signInAnonymously() returns success, " +
            "then anonymousLogin() should return AuthResponse",
    )
    fun testAnonymousLoginSuccess() =
        runTest(testDispatcher) {
            val result =
                mockk<AuthResult>(relaxed = true) {
                    every { user?.isAnonymous } returns true
                }

            every { firebaseAuth.signInAnonymously() } returns
                mockTaskResult(result)

            registerService.anonymousLogin() shouldBe RegisterResponse.Success(isAnonymous = true)
        }

    @Test
    @DisplayName(
        "When firebaseAuth.signInAnonymously() returns exception, " +
            "then anonymousLogin() should return exception",
    )
    fun testAnonymousLoginError() =
        runTest(testDispatcher) {
            every { firebaseAuth.signInAnonymously() } returns
                mockTaskError<AuthResult>(Exception())

            registerService.anonymousLogin() shouldBe RegisterResponse.Error(code = "", message = "")
        }

    @Test
    @DisplayName(
        "When firebaseAuth.createUserWithEmailAndPassword() returns success, " +
            "then emailPasswordRegister() should return AuthResponse",
    )
    fun testEmailPasswordRegisterSuccess() =
        runTest(testDispatcher) {
            val result =
                mockk<AuthResult>(relaxed = true) {
                    every { user?.isAnonymous } returns true
                }

            every { firebaseAuth.createUserWithEmailAndPassword(any(), any()) } returns
                mockTaskResult(result)

            registerService.emailPasswordRegister(
                email = "test@domain.com",
                password = "*****",
            ) shouldBe RegisterResponse.Success(isAnonymous = false)
        }

    @Test
    @DisplayName(
        "When firebaseAuth.createUserWithEmailAndPassword() returns FirebaseAuthException, " +
            "then emailPasswordRegister() should return error with error code",
    )
    fun testEmailPasswordRegisterErrorFirebaseAuthException() =
        runTest(testDispatcher) {
            val firebaseAuthException =
                mockk<FirebaseAuthException> {
                    every { errorCode } returns "error-code"
                    every { message } returns "message"
                }

            every { firebaseAuth.createUserWithEmailAndPassword(any(), any()) } returns
                mockTaskError<AuthResult>(firebaseAuthException)

            registerService.emailPasswordRegister(
                email = "test@domain.com",
                password = "*****",
            ) shouldBe RegisterResponse.Error(code = "error-code", message = "")
        }

    @Test
    @DisplayName(
        "When firebaseAuth.createUserWithEmailAndPassword() returns Exception, " +
            "then emailPasswordRegister() should return error with empty error code",
    )
    fun testEmailPasswordRegisterErrorException() =
        runTest(testDispatcher) {
            every { firebaseAuth.createUserWithEmailAndPassword(any(), any()) } returns
                mockTaskError<AuthResult>(Exception())

            registerService.emailPasswordRegister(
                email = "test@domain.com",
                password = "*****",
            ) shouldBe RegisterResponse.Error(code = "", message = "")
        }
}
