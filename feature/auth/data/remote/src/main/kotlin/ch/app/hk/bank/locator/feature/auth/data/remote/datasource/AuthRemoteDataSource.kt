package ch.app.hk.bank.locator.feature.auth.data.remote.datasource

import ch.app.hk.bank.locator.feature.auth.data.remote.response.AuthResponse

interface AuthRemoteDataSource {
    fun isAuthorized(): Boolean

    suspend fun anonymousLogin(): Result<AuthResponse>

    suspend fun emailPasswordRegister(
        email: String,
        password: String,
    ): Result<AuthResponse>
}
