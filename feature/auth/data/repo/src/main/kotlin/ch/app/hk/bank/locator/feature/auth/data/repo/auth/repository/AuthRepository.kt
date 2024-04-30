package ch.app.hk.bank.locator.feature.auth.data.repo.auth.repository

interface AuthRepository {
    suspend fun isAuthInitialized(): Boolean

    suspend fun setAuthInitialized()
}
