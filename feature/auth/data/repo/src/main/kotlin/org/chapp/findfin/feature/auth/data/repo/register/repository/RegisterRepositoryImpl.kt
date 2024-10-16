package org.chapp.findfin.feature.auth.data.repo.register.repository

import org.chapp.findfin.feature.auth.data.repo.register.model.RegisterResult
import org.chapp.findfin.feature.auth.data.repo.register.remote.datasource.RegisterRemoteDataSource
import org.chapp.findfin.feature.auth.data.repo.register.remote.response.RegisterResponse
import org.chapp.library.hiltwrap.annotation.HiltWrapBindModule
import javax.inject.Inject

@HiltWrapBindModule
internal class RegisterRepositoryImpl @Inject constructor(
    private val registerRemoteDataSource: RegisterRemoteDataSource,
) : RegisterRepository {
    override suspend fun emailPasswordRegister(
        email: String,
        password: String,
    ): RegisterResult {
        val response =
            registerRemoteDataSource.emailPasswordRegister(
                email = email,
                password = password,
            )

        return when (response) {
            RegisterResponse.Error.InvalidEmail -> RegisterResult.Error.Register.InvalidEmail
            RegisterResponse.Error.Unknown -> RegisterResult.Error.Unknown
            RegisterResponse.Error.UserCollision -> RegisterResult.Error.Register.EmailAlreadyInUse
            RegisterResponse.Error.WeakPassword -> RegisterResult.Error.Register.WeakPassword
            RegisterResponse.Success -> RegisterResult.Authorized
        }
    }
}
