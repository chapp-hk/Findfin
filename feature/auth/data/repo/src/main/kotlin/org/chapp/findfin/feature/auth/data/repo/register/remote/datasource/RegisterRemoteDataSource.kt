package org.chapp.findfin.feature.auth.data.repo.register.remote.datasource

import org.chapp.findfin.feature.auth.data.repo.register.remote.response.RegisterResponse

interface RegisterRemoteDataSource {
    suspend fun emailPasswordRegister(
        email: String,
        password: String,
    ): RegisterResponse
}
