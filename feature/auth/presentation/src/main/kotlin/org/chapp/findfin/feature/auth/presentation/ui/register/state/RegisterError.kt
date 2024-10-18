package org.chapp.findfin.feature.auth.presentation.ui.register.state

internal enum class RegisterError {
    UNKNOWN,
    INVALID_EMAIL,
    WEAK_PASSWORD,
    EMAIL_ALREADY_IN_USE,
}
