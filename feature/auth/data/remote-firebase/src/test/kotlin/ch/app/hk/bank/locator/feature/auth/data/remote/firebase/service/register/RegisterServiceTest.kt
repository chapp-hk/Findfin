package ch.app.hk.bank.locator.feature.auth.data.remote.firebase.service.register

import ch.app.hk.bank.locator.feature.auth.data.repo.register.remote.response.RegisterResponse
import ch.app.hk.bank.locator.testing.google.play.services.task.mockTaskError
import ch.app.hk.bank.locator.testing.google.play.services.task.mockTaskResult
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthException
import io.kotest.matchers.shouldBe
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test

@DisplayName("RegisterService unit tests")
class RegisterServiceTest {
    private val testDispatcher = StandardTestDispatcher()
    private val firebaseAuth = mockk<FirebaseAuth>()

    private val registerService =
        RegisterService(
            ioDispatcher = testDispatcher,
            firebaseAuth = firebaseAuth,
        )

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
