package ch.app.hk.bank.locator.feature.auth.data.remote.login.datasource

import ch.app.hk.bank.locator.feature.auth.data.remote.login.response.LoginResponse

interface LoginRemoteDataSource {
    suspend fun emailPasswordLogin(
        email: String,
        password: String,
    ): LoginResponse
}
