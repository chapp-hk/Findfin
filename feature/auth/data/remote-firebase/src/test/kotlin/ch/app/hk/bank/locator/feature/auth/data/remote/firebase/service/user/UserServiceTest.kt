package ch.app.hk.bank.locator.feature.auth.data.remote.firebase.service.user

import ch.app.hk.bank.locator.feature.auth.data.remote.user.response.UserResponse
import com.google.firebase.auth.FirebaseAuth
import io.kotest.matchers.shouldBe
import io.kotest.matchers.types.instanceOf
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test

@DisplayName("UserService unit tests")
class UserServiceTest {
    private val testDispatcher = StandardTestDispatcher()
    private val firebaseAuth = mockk<FirebaseAuth>()

    private val userService =
        UserService(
            ioDispatcher = testDispatcher,
            firebaseAuth = firebaseAuth,
        )

    @Test
    @DisplayName(
        "when firebaseAuth currentUser return non null value, " +
            "getCurrentUser() should return non null UserResponse",
    )
    fun `test getCurrentUser(), non null case`() =
        runTest(testDispatcher) {
            every { firebaseAuth.currentUser } returns mockk(relaxed = true)

            userService.getCurrentUser() shouldBe instanceOf<UserResponse>()
        }

    @Test
    @DisplayName(
        "when firebaseAuth currentUser return null, " +
            "getCurrentUser() should return null",
    )
    fun `test getCurrentUser(), null case`() =
        runTest(testDispatcher) {
            every { firebaseAuth.currentUser } returns null

            userService.getCurrentUser() shouldBe null
        }
}
