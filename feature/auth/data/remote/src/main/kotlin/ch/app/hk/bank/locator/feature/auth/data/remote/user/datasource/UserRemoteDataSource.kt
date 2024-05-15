package ch.app.hk.bank.locator.feature.auth.data.remote.user.datasource

import ch.app.hk.bank.locator.feature.auth.data.remote.user.response.UserResponse

interface UserRemoteDataSource {
    suspend fun getCurrentUser(): UserResponse?
}
