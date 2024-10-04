package org.chapp.findfin.feature.auth.data.repo.login.repository

import org.chapp.findfin.feature.auth.data.repo.login.model.LoginResult

interface LoginRepository {
    suspend fun emailPasswordLogin(
        email: String,
        password: String,
    ): LoginResult
}
