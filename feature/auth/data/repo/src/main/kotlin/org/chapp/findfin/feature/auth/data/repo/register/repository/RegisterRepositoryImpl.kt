package org.chapp.findfin.feature.auth.data.repo.register.repository

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import org.chapp.findfin.core.threading.DispatcherIo
import org.chapp.findfin.feature.auth.data.repo.register.model.RegisterResult
import org.chapp.findfin.feature.auth.data.repo.register.remote.datasource.RegisterRemoteDataSource
import org.chapp.findfin.feature.auth.data.repo.register.remote.response.RegisterResponse
import org.chapp.library.hiltwrap.annotation.HiltWrapBindModule
import javax.inject.Inject

@HiltWrapBindModule
internal class RegisterRepositoryImpl @Inject constructor(
    @param:DispatcherIo private val ioDispatcher: CoroutineDispatcher,
    private val registerRemoteDataSource: RegisterRemoteDataSource,
) : RegisterRepository {
    override suspend fun emailPasswordRegister(
        email: String,
        password: String,
    ): RegisterResult {
        val response =
            withContext(context = ioDispatcher) {
                registerRemoteDataSource.emailPasswordRegister(
                    email = email,
                    password = password,
                )
            }

        return when (response) {
            RegisterResponse.Error.InvalidEmail -> RegisterResult.Error.InvalidEmail
            RegisterResponse.Error.Unknown -> RegisterResult.Error.Unknown
            RegisterResponse.Error.UserCollision -> RegisterResult.Error.EmailAlreadyInUse
            RegisterResponse.Error.WeakPassword -> RegisterResult.Error.WeakPassword
            RegisterResponse.Success -> RegisterResult.Authorized
        }
    }
}
