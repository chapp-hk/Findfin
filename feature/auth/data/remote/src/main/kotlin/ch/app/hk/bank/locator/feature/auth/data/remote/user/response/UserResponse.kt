package ch.app.hk.bank.locator.feature.auth.data.remote.user.response

data class UserResponse(
    val displayName: String,
    val email: String,
    val isEmailVerified: Boolean,
)
