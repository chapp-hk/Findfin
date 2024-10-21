package org.chapp.findfin.feature.auth.data.repo.register.remote.datasource

import org.chapp.findfin.feature.auth.data.repo.register.remote.response.RegisterResponse

/**
 * Interface representing a remote data source for registration operations.
 */
interface RegisterRemoteDataSource {
    /**
     * Performs a registration operation using email and password.
     *
     * @param email The email address used for registration.
     * @param password The password used for registration.
     * @return A [RegisterResponse] representing the result of the registration operation.
     */
    suspend fun emailPasswordRegister(
        email: String,
        password: String,
    ): RegisterResponse
}
