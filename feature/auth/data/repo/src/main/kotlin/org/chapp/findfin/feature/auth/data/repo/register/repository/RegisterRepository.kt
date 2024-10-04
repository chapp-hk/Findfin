package org.chapp.findfin.feature.auth.data.repo.register.repository

import org.chapp.findfin.feature.auth.data.repo.register.model.RegisterResult

interface RegisterRepository {
    suspend fun emailPasswordRegister(
        email: String,
        password: String,
    ): RegisterResult
}
