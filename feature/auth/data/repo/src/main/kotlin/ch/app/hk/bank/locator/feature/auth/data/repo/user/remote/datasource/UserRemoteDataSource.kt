package ch.app.hk.bank.locator.feature.auth.data.repo.user.remote.datasource

import ch.app.hk.bank.locator.feature.auth.data.repo.user.remote.response.UserResponse

interface UserRemoteDataSource {
    suspend fun getCurrentUser(): UserResponse?
}
