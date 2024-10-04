package org.chapp.findfin.feature.auth.data.repo.login.remote.datasource

import org.chapp.findfin.feature.auth.data.repo.login.remote.response.LoginResponse

interface LoginRemoteDataSource {
    suspend fun emailPasswordLogin(
        email: String,
        password: String,
    ): LoginResponse
}
