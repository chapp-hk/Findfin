package ch.app.hk.bank.locator.feature.auth.data.remote.response

sealed interface AuthResponse {
    data class Success(
        val isAnonymous: Boolean,
    ) : AuthResponse

    data class Error(
        val code: String,
        val message: String,
    ) : AuthResponse
}
