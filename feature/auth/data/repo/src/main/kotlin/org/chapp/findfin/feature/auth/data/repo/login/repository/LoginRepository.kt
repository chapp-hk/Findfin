package org.chapp.findfin.feature.auth.data.repo.login.repository

import org.chapp.findfin.feature.auth.data.repo.login.model.LoginResult

/**
 * Interface representing a repository for login operations.
 */
interface LoginRepository {
    /**
     * Performs a login operation using email and password.
     *
     * @param email The email address used for login.
     * @param password The password used for login.
     * @return A [LoginResult] representing the result of the login operation.
     */
    suspend fun emailPasswordLogin(
        email: String,
        password: String,
    ): LoginResult
}
