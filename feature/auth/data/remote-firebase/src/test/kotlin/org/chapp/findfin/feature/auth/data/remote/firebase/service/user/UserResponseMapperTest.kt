package org.chapp.findfin.feature.auth.data.remote.firebase.service.user

import com.google.firebase.auth.FirebaseUser
import io.kotest.matchers.shouldBe
import io.mockk.every
import io.mockk.mockk
import org.chapp.findfin.feature.auth.data.repo.user.remote.response.UserResponse
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test

@DisplayName("UserResponseMapper unit tests")
class UserResponseMapperTest {
    private val mapper = UserResponseMapper()

    @Test
    fun `test toUserResponse with non null values`() {
        val user =
            mockk<FirebaseUser> {
                every { displayName } returns "mock display name"
                every { email } returns "test@email.com"
                every { isEmailVerified } returns false
            }

        mapper.toUserResponse(user) shouldBe
            UserResponse(
                displayName = "mock display name",
                email = "test@email.com",
                isEmailVerified = false,
            )
    }

    @Test
    fun `test toUserResponse with null values`() {
        val user =
            mockk<FirebaseUser> {
                every { displayName } returns null
                every { email } returns null
                every { isEmailVerified } returns false
            }

        mapper.toUserResponse(user) shouldBe
            UserResponse(
                displayName = "",
                email = "",
                isEmailVerified = false,
            )
    }
}
