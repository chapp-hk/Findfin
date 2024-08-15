package ch.app.hk.bank.locator.feature.auth.data.repo.login.repository

import ch.app.hk.bank.locator.feature.auth.data.remote.login.datasource.LoginRemoteDataSource
import ch.app.hk.bank.locator.feature.auth.data.remote.login.response.LoginResponse
import ch.app.hk.bank.locator.feature.auth.data.repo.login.model.LoginResult
import ch.app.library.hiltwrap.annotation.HiltExtBindModule
import javax.inject.Inject

@HiltExtBindModule
class LoginRepositoryImpl @Inject constructor(
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
