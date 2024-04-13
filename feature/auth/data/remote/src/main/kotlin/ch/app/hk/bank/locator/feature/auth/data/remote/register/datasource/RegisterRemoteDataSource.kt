package ch.app.hk.bank.locator.feature.auth.data.remote.register.datasource

import ch.app.hk.bank.locator.feature.auth.data.remote.register.response.RegisterResponse

interface RegisterRemoteDataSource {
    suspend fun emailPasswordRegister(
        email: String,
        password: String,
    ): RegisterResponse
}
