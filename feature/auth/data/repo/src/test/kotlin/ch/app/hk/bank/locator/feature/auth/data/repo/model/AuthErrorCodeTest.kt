package ch.app.hk.bank.locator.feature.auth.data.repo.model

import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.extension.ExtensionContext
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.ArgumentsProvider
import org.junit.jupiter.params.provider.ArgumentsSource
import java.util.stream.Stream

@DisplayName("AuthErrorCode unit tests")
class AuthErrorCodeTest {
    @ParameterizedTest(
        name = "When errorCodeValue is {0}, then AuthErrorCode.fromString() should return {1}",
    )
    @ArgumentsSource(AuthErrorCodeProvider::class)
    fun `test fromString`(
        errorCodeValue: String,
        expectedResult: AuthErrorCode,
    ) {
        AuthErrorCode.fromString(errorCodeValue) shouldBe expectedResult
    }

    private class AuthErrorCodeProvider : ArgumentsProvider {
        override fun provideArguments(context: ExtensionContext): Stream<Arguments> =
            Stream.of(
                Arguments.arguments(
                    "",
                    AuthErrorCode.ERROR_UNKNOWN,
                ),
                Arguments.arguments(
                    "ERROR_UNKNOWN",
                    AuthErrorCode.ERROR_UNKNOWN,
                ),
                Arguments.arguments(
                    "ERROR_INVALID_EMAIL",
                    AuthErrorCode.ERROR_INVALID_EMAIL,
                ),
                Arguments.arguments(
                    "ERROR_WEAK_PASSWORD",
                    AuthErrorCode.ERROR_WEAK_PASSWORD,
                ),
                Arguments.arguments(
                    "ERROR_EMAIL_ALREADY_IN_USE",
                    AuthErrorCode.ERROR_EMAIL_ALREADY_IN_USE,
                ),
            )
    }
}
