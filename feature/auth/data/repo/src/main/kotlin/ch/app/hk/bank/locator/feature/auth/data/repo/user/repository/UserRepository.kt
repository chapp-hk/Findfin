package ch.app.hk.bank.locator.feature.auth.data.repo.user.repository

import ch.app.hk.bank.locator.feature.auth.data.repo.user.model.UserModel

interface UserRepository {
    suspend fun getCurrentUser(): UserModel?
}
