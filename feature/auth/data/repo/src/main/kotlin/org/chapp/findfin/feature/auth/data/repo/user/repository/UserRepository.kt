package org.chapp.findfin.feature.auth.data.repo.user.repository

import org.chapp.findfin.feature.auth.data.repo.user.model.UserModel

/**
 * Interface representing a repository for user operations.
 */
interface UserRepository {
    /**
     * Retrieves the current user.
     *
     * @return A [UserModel] representing the current user, or null if no user is logged in.
     */
    suspend fun getCurrentUser(): UserModel?
}
