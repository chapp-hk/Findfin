package org.chapp.findfin.feature.auth.data.repo.register.remote.response

sealed interface RegisterResponse {
    data object Success : RegisterResponse

    data class Error(
        val code: String,
        val message: String,
    ) : RegisterResponse
}
