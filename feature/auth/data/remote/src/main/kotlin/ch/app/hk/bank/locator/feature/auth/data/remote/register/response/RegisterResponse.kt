package ch.app.hk.bank.locator.feature.auth.data.remote.register.response

sealed interface RegisterResponse {
    data class Success(
        val isAnonymous: Boolean,
    ) : RegisterResponse

    data class Error(
        val code: String,
        val message: String,
    ) : RegisterResponse
}
