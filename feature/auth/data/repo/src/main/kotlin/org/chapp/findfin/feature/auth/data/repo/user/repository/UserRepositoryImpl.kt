package org.chapp.findfin.feature.auth.data.repo.user.repository

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import org.chapp.findfin.core.threading.DispatcherIo
import org.chapp.findfin.feature.auth.data.repo.user.mapper.UserModelMapper
import org.chapp.findfin.feature.auth.data.repo.user.model.UserModel
import org.chapp.findfin.feature.auth.data.repo.user.remote.datasource.UserRemoteDataSource
import org.chapp.library.hiltwrap.annotation.HiltWrapBindModule
import org.mapstruct.factory.Mappers
import javax.inject.Inject

@HiltWrapBindModule
internal class UserRepositoryImpl @Inject constructor(
    @param:DispatcherIo private val ioDispatcher: CoroutineDispatcher,
    private val userRemoteDataSource: UserRemoteDataSource,
) : UserRepository {
    override suspend fun getCurrentUser(): UserModel? {
        return withContext(ioDispatcher) {
            val mapper = Mappers.getMapper(UserModelMapper::class.java)
            userRemoteDataSource.getCurrentUser()?.let(mapper::clone)
        }
    }
}
