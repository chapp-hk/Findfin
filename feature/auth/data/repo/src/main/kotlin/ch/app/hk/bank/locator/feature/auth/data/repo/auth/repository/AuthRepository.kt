package ch.app.hk.bank.locator.feature.auth.data.repo.auth.repository

import kotlinx.coroutines.flow.Flow

interface AuthRepository {
    fun isAuthInitialized(): Flow<Boolean>

    suspend fun setAuthInitialized()
}
