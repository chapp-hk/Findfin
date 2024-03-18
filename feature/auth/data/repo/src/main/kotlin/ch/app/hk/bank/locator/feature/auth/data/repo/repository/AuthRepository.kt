package ch.app.hk.bank.locator.feature.auth.data.repo.repository

import ch.app.hk.bank.locator.feature.auth.data.repo.model.AuthResult

interface AuthRepository {
    fun isAuthorized(): Boolean

    suspend fun anonymousLogin(): AuthResult

    suspend fun emailPasswordRegister(
        email: String,
        password: String,
    ): AuthResult
}
