package ch.app.hk.bank.locator.feature.auth.data.repo.user.repository

import ch.app.framework.hiltext.annotation.HiltExtBindModule
import ch.app.hk.bank.locator.feature.auth.data.remote.user.datasource.UserRemoteDataSource
import ch.app.hk.bank.locator.feature.auth.data.repo.user.mapper.UserModelMapper
import ch.app.hk.bank.locator.feature.auth.data.repo.user.model.UserModel
import org.mapstruct.factory.Mappers
import javax.inject.Inject

@HiltExtBindModule
internal class UserRepositoryImpl @Inject constructor(
    private val userRemoteDataSource: UserRemoteDataSource,
) : UserRepository {
    override suspend fun getCurrentUser(): UserModel? {
        val mapper = Mappers.getMapper(UserModelMapper::class.java)
        return userRemoteDataSource.getCurrentUser()?.let(mapper::clone)
    }
}
