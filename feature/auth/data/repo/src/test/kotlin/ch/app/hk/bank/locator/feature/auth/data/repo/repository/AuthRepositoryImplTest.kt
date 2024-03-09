package ch.app.hk.bank.locator.feature.auth.data.repo.repository

import ch.app.hk.bank.locator.feature.auth.data.remote.datasource.AuthRemoteDataSource
import ch.app.hk.bank.locator.feature.auth.data.repo.model.AuthResult
import io.kotest.matchers.shouldBe
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtensionContext
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.ArgumentsProvider
import org.junit.jupiter.params.provider.ArgumentsSource
import java.util.stream.Stream

@DisplayName("AuthRepositoryImpl unit tests")
class AuthRepositoryImplTest {
    private val authRemoteDataSource = mockk<AuthRemoteDataSource>()

    private val authRepository = AuthRepositoryImpl(authRemoteDataSource = authRemoteDataSource)

    @ParameterizedTest(
        name =
            "When authRemoteDataSource.isAuthorized() returns {0}, " +
                "then isAuthorized() should return {1}",
    )
    @ArgumentsSource(IsAuthorizedProvider::class)
    fun testIsAuthorized(
        mockIsAuthorized: Boolean,
        expectedResult: Boolean,
    ) {
        every { authRemoteDataSource.isAuthorized() } returns mockIsAuthorized

        authRepository.isAuthorized() shouldBe expectedResult
    }

    private class IsAuthorizedProvider : ArgumentsProvider {
        override fun provideArguments(context: ExtensionContext): Stream<Arguments> =
            Stream.of(
                Arguments.arguments(true, true),
                Arguments.arguments(false, false),
            )
    }

    @Test
    fun testAnonymousLoginSuccess() =
        runTest(StandardTestDispatcher()) {
            coEvery { authRemoteDataSource.anonymousLogin() } returns Result.success(mockk(relaxed = true))

            authRepository.anonymousLogin() shouldBe AuthResult.Authorized
        }

    @Test
    fun testAnonymousLoginError() =
        runTest(StandardTestDispatcher()) {
            val error = Throwable("some error")
            coEvery { authRemoteDataSource.anonymousLogin() } returns Result.failure(error)

            authRepository.anonymousLogin() shouldBe AuthResult.Failed("some error")
        }
}
