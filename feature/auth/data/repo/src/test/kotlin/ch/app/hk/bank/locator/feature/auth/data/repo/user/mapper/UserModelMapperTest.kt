package ch.app.hk.bank.locator.feature.auth.data.repo.user.mapper

import ch.app.hk.bank.locator.feature.auth.data.remote.user.response.UserResponse
import ch.app.hk.bank.locator.feature.auth.data.repo.user.model.UserModel
import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.mapstruct.factory.Mappers

@DisplayName("UserModelMapper unit tests")
class UserModelMapperTest {
    private val userModelMapper = Mappers.getMapper(UserModelMapper::class.java)

    @Test
    fun `test clone with isEmailVerified=true`() {
        val userResponse =
            UserResponse(
                displayName = "mock display name",
                email = "test@email.com",
                isEmailVerified = true,
            )

        userModelMapper.clone(userResponse) shouldBe
            UserModel(
                displayName = "mock display name",
                email = "test@email.com",
                isEmailVerified = true,
            )
    }

    @Test
    fun `test clone with isEmailVerified=false`() {
        val userResponse =
            UserResponse(
                displayName = "mock display name",
                email = "test@email.com",
                isEmailVerified = false,
            )

        userModelMapper.clone(userResponse) shouldBe
            UserModel(
                displayName = "mock display name",
                email = "test@email.com",
                isEmailVerified = false,
            )
    }
}
