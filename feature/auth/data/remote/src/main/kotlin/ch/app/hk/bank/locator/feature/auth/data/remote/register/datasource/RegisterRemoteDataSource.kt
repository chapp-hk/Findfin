package ch.app.hk.bank.locator.feature.auth.data.remote.register.datasource

import ch.app.hk.bank.locator.feature.auth.data.remote.register.response.RegisterResponse

interface RegisterRemoteDataSource {
    fun isAuthorized(): Boolean

    suspend fun anonymousLogin(): RegisterResponse

    suspend fun emailPasswordRegister(
        email: String,
        password: String,
    ): RegisterResponse
}
