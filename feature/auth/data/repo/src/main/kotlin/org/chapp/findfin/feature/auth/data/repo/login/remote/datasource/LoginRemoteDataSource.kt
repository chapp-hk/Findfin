package org.chapp.findfin.feature.auth.data.repo.login.remote.datasource

import org.chapp.findfin.feature.auth.data.repo.login.remote.response.LoginResponse

/**
 * Interface representing a remote data source for login operations.
 */
interface LoginRemoteDataSource {
    /**
     * Performs a login operation using email and password.
     *
     * @param email The email address used for login.
     * @param password The password used for login.
     * @return A [LoginResponse] representing the result of the login operation.
     */
    suspend fun emailPasswordLogin(
        email: String,
        password: String,
    ): LoginResponse
}
