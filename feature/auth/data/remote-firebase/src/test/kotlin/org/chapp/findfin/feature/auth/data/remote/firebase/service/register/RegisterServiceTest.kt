package org.chapp.findfin.feature.auth.data.remote.firebase.service.register

import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthException
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import com.google.firebase.auth.FirebaseAuthWeakPasswordException
import io.kotest.matchers.shouldBe
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.chapp.findfin.feature.auth.data.repo.register.remote.response.RegisterResponse
import org.chapp.findfin.testing.google.play.services.task.mockTaskError
import org.chapp.findfin.testing.google.play.services.task.mockTaskResult
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test

@DisplayName("RegisterService unit tests")
class RegisterServiceTest {
    private val firebaseAuth = mockk<FirebaseAuth>()

    private val registerService =
        RegisterService(
            firebaseAuth = firebaseAuth,
        )

    @Test
    @DisplayName(
        "When firebaseAuth.createUserWithEmailAndPassword() returns success, " +
            "then emailPasswordRegister() should return AuthResponse",
    )
    fun testEmailPasswordRegisterSuccess() =
        runTest {
            val result =
                mockk<AuthResult>(relaxed = true) {
                    every { user?.isAnonymous } returns true
                }

            every { firebaseAuth.createUserWithEmailAndPassword(any(), any()) } returns
                mockTaskResult(result)

            registerService.emailPasswordRegister(
                email = "test@domain.com",
                password = "*****",
            ) shouldBe RegisterResponse.Success
        }

    @Test
    @DisplayName(
        "When firebaseAuth.createUserWithEmailAndPassword() returns FirebaseAuthWeakPasswordException, " +
            "then emailPasswordRegister() should return RegisterResponse.Error.WeakPassword",
    )
    fun testEmailPasswordRegisterErrorWeakPassword() =
        runTest {
            val exception = mockk<FirebaseAuthWeakPasswordException>()

            every { firebaseAuth.createUserWithEmailAndPassword(any(), any()) } returns
                mockTaskError<AuthResult>(exception)

            registerService.emailPasswordRegister(
                email = "test@domain.com",
                password = "*****",
            ) shouldBe RegisterResponse.Error.WeakPassword
        }

    @Test
    @DisplayName(
        "When firebaseAuth.createUserWithEmailAndPassword() returns FirebaseAuthInvalidCredentialsException, " +
            "then emailPasswordRegister() should return RegisterResponse.Error.InvalidEmail",
    )
    fun testEmailPasswordRegisterErrorInvalidEmail() =
        runTest {
            val exception = mockk<FirebaseAuthInvalidCredentialsException>()

            every { firebaseAuth.createUserWithEmailAndPassword(any(), any()) } returns
                mockTaskError<AuthResult>(exception)

            registerService.emailPasswordRegister(
                email = "test@domain.com",
                password = "*****",
            ) shouldBe RegisterResponse.Error.InvalidEmail
        }

    @Test
    @DisplayName(
        "When firebaseAuth.createUserWithEmailAndPassword() returns FirebaseAuthUserCollisionException, " +
            "then emailPasswordRegister() should return RegisterResponse.Error.UserCollision",
    )
    fun testEmailPasswordRegisterErrorUserCollision() =
        runTest {
            val exception = mockk<FirebaseAuthUserCollisionException>()

            every { firebaseAuth.createUserWithEmailAndPassword(any(), any()) } returns
                mockTaskError<AuthResult>(exception)

            registerService.emailPasswordRegister(
                email = "test@domain.com",
                password = "*****",
            ) shouldBe RegisterResponse.Error.UserCollision
        }

    @Test
    @DisplayName(
        "When firebaseAuth.createUserWithEmailAndPassword() returns any exception, " +
            "then emailPasswordRegister() should return RegisterResponse.Error.Unknown",
    )
    fun testEmailPasswordRegisterErrorUnknown() =
        runTest {
            val exception = mockk<FirebaseAuthException>()

            every { firebaseAuth.createUserWithEmailAndPassword(any(), any()) } returns
                mockTaskError<AuthResult>(exception)

            registerService.emailPasswordRegister(
                email = "test@domain.com",
                password = "*****",
            ) shouldBe RegisterResponse.Error.Unknown
        }
}
