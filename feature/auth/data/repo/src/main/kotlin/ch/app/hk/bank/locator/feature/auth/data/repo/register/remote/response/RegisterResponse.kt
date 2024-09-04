package ch.app.hk.bank.locator.feature.auth.data.repo.register.remote.response

sealed interface RegisterResponse {
    data class Success(
        val isAnonymous: Boolean,
    ) : RegisterResponse

    data class Error(
        val code: String,
        val message: String,
    ) : RegisterResponse
}
