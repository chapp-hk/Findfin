package ch.app.hk.bank.locator.feature.auth.data.repo.model

enum class AuthErrorCode {
    ERROR_UNKNOWN,
    ERROR_INVALID_EMAIL,
    ERROR_WEAK_PASSWORD,
    ERROR_EMAIL_ALREADY_IN_USE,
    ;

    companion object {
        fun fromString(value: String): AuthErrorCode {
            return runCatching { valueOf(value) }.getOrDefault(ERROR_UNKNOWN)
        }
    }
}
