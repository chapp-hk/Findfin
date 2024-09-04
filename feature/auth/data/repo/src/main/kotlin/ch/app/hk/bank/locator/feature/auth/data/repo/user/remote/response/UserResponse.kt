package ch.app.hk.bank.locator.feature.auth.data.repo.user.remote.response

data class UserResponse(
    val displayName: String,
    val email: String,
    val isEmailVerified: Boolean,
)
