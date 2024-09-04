package ch.app.hk.bank.locator.feature.auth.data.repo.login.remote.datasource

import ch.app.hk.bank.locator.feature.auth.data.repo.login.remote.response.LoginResponse

interface LoginRemoteDataSource {
    suspend fun emailPasswordLogin(
        email: String,
        password: String,
    ): LoginResponse
}
