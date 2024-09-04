package ch.app.hk.bank.locator.feature.auth.data.repo.user.repository

import ch.app.hk.bank.locator.feature.auth.data.repo.user.model.UserModel
import ch.app.hk.bank.locator.feature.auth.data.repo.user.remote.datasource.UserRemoteDataSource
import io.kotest.matchers.shouldBe
import io.kotest.matchers.types.instanceOf
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test

@DisplayName("UserRepositoryImpl unit tests")
class UserRepositoryImplTest {
    private val userRemoteDataSource = mockk<UserRemoteDataSource>()

    private val userRepositoryImpl = UserRepositoryImpl(userRemoteDataSource = userRemoteDataSource)

    @Test
    @DisplayName(
        "when userRemoteDataSource getCurrentUser() return non null value, " +
            "getCurrentUser() should return non null UserResponse",
    )
    fun `test getCurrentUser(), non null case`() =
        runTest(StandardTestDispatcher()) {
            coEvery { userRemoteDataSource.getCurrentUser() } returns mockk(relaxed = true)

            userRepositoryImpl.getCurrentUser() shouldBe instanceOf<UserModel>()
        }

    @Test
    @DisplayName(
        "when userRemoteDataSource getCurrentUser() return null, " +
            "getCurrentUser() should return null",
    )
    fun `test getCurrentUser(), null case`() =
        runTest(StandardTestDispatcher()) {
            coEvery { userRemoteDataSource.getCurrentUser() } returns null

            userRepositoryImpl.getCurrentUser() shouldBe null
        }
}
