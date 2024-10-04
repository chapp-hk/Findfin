package org.chapp.findfin.feature.auth.data.repo.register.model

enum class RegisterErrorCode {
    ERROR_UNKNOWN,
    ERROR_INVALID_EMAIL,
    ERROR_WEAK_PASSWORD,
    ERROR_EMAIL_ALREADY_IN_USE,
    ;

    companion object {
        fun fromString(value: String): RegisterErrorCode {
            return runCatching { valueOf(value) }.getOrDefault(ERROR_UNKNOWN)
        }
    }
}
