package ch.app.hk.bank.locator.feature.auth.data.repo.register.remote.datasource

import ch.app.hk.bank.locator.feature.auth.data.repo.register.remote.response.RegisterResponse

interface RegisterRemoteDataSource {
    suspend fun emailPasswordRegister(
        email: String,
        password: String,
    ): RegisterResponse
}
