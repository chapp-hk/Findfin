package org.chapp.findfin.feature.auth.data.repo.login.repository

import org.chapp.findfin.feature.auth.data.repo.login.model.LoginResult
import org.chapp.findfin.feature.auth.data.repo.login.remote.datasource.LoginRemoteDataSource
import org.chapp.findfin.feature.auth.data.repo.login.remote.response.LoginResponse
import org.chapp.library.hiltwrap.annotation.HiltWrapBindModule
import javax.inject.Inject

@HiltWrapBindModule
internal class LoginRepositoryImpl @Inject constructor(
    private val loginRemoteDataSource: LoginRemoteDataSource,
) : LoginRepository {
    override suspend fun emailPasswordLogin(
        email: String,
        password: String,
    ): LoginResult {
        val loginResponse =
            loginRemoteDataSource.emailPasswordLogin(
                email = email,
                password = password,
            )

        return when (loginResponse) {
            LoginResponse.Error.AccountDisabled -> LoginResult.Error.AccountDisabled
            LoginResponse.Error.InvalidCredential -> LoginResult.Error.InvalidCredential
            LoginResponse.Error.TooManyRequest -> LoginResult.Error.TooManyRequest
            LoginResponse.Error.Unknown -> LoginResult.Error.Unknown
            LoginResponse.Success -> LoginResult.Success
        }
    }
}
