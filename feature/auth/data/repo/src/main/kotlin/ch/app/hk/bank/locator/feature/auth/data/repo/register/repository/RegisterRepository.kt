package ch.app.hk.bank.locator.feature.auth.data.repo.register.repository

import ch.app.hk.bank.locator.feature.auth.data.repo.register.model.RegisterResult

interface RegisterRepository {
    fun isAuthorized(): Boolean

    suspend fun anonymousLogin(): RegisterResult

    suspend fun emailPasswordRegister(
        email: String,
        password: String,
    ): RegisterResult
}
