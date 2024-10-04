package org.chapp.findfin.feature.auth.data.repo.user.repository

import org.chapp.findfin.feature.auth.data.repo.user.model.UserModel

interface UserRepository {
    suspend fun getCurrentUser(): UserModel?
}
