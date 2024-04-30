package ch.app.hk.bank.locator.feature.auth.data.repo.login.repository

import ch.app.hk.bank.locator.feature.auth.data.repo.login.model.LoginResult

interface LoginRepository {
    suspend fun emailPasswordLogin(
        email: String,
        password: String,
    ): LoginResult
}
