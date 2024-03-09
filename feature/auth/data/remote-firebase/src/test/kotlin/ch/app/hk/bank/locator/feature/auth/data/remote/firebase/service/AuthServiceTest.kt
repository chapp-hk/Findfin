package ch.app.hk.bank.locator.feature.auth.data.remote.firebase.service

import ch.app.hk.bank.locator.feature.auth.data.remote.response.AuthResponse
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
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
class AuthServiceTest {
    private val testDispatcher = StandardTestDispatcher()
    private val firebaseAuth = mockk<FirebaseAuth>()

    private val authService =
        AuthService(
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

        authService.isAuthorized() shouldBe expectedResult
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
            val task = mockk<Task<AuthResult>>()
            every { task.isComplete } returns true
            every { task.exception } returns null
            every { task.isCanceled } returns false
            every { task.result } returns mockk(relaxed = true)
            every { task.result.user?.isAnonymous } returns true

            every { firebaseAuth.signInAnonymously() } returns task

            authService.anonymousLogin().getOrThrow() shouldBe AuthResponse(isAnonymous = true)
        }

    @Test
    @DisplayName(
        "When firebaseAuth.signInAnonymously() returns exception, " +
            "then anonymousLogin() should return exception",
    )
    fun testAnonymousLoginError() =
        runTest(testDispatcher) {
            val task = mockk<Task<AuthResult>>()
            every { task.isComplete } returns true
            every { task.exception } returns Exception()

            every { firebaseAuth.signInAnonymously() } returns task

            authService.anonymousLogin().exceptionOrNull() shouldBe Exception()
        }
}
