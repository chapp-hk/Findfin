package org.chapp.findfin.feature.auth.data.repo.user.repository

import org.chapp.findfin.feature.auth.data.repo.user.mapper.UserModelMapper
import org.chapp.findfin.feature.auth.data.repo.user.model.UserModel
import org.chapp.findfin.feature.auth.data.repo.user.remote.datasource.UserRemoteDataSource
import org.chapp.library.hiltwrap.annotation.HiltWrapBindModule
import org.mapstruct.factory.Mappers
import javax.inject.Inject

@HiltWrapBindModule
internal class UserRepositoryImpl @Inject constructor(
    private val userRemoteDataSource: UserRemoteDataSource,
) : UserRepository {
    override suspend fun getCurrentUser(): UserModel? {
        val mapper = Mappers.getMapper(UserModelMapper::class.java)
        return userRemoteDataSource.getCurrentUser()?.let(mapper::clone)
    }
}
