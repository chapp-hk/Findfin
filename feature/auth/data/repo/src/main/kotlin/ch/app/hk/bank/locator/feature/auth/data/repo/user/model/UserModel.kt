package ch.app.hk.bank.locator.feature.auth.data.repo.user.model

data class UserModel(
    val displayName: String,
    val email: String,
    val isEmailVerified: Boolean,
)
