package org.chapp.findfin.feature.auth.data.repo.user.remote.datasource

import org.chapp.findfin.feature.auth.data.repo.user.remote.response.UserResponse

interface UserRemoteDataSource {
    suspend fun getCurrentUser(): UserResponse?
}
