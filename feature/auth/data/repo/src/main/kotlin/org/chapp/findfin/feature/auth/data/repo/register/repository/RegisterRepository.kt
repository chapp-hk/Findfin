package org.chapp.findfin.feature.auth.data.repo.register.repository

import org.chapp.findfin.feature.auth.data.repo.register.model.RegisterResult

/**
 * Interface representing a repository for registration operations.
 */
interface RegisterRepository {
    /**
     * Performs a registration operation using email and password.
     *
     * @param email The email address used for registration.
     * @param password The password used for registration.
     * @return A [RegisterResult] representing the result of the registration operation.
     */
    suspend fun emailPasswordRegister(
        email: String,
        password: String,
    ): RegisterResult
}
