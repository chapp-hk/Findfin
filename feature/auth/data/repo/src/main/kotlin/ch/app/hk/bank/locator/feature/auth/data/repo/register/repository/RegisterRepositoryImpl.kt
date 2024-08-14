package ch.app.hk.bank.locator.feature.auth.data.repo.register.repository

import ch.app.hk.bank.locator.feature.auth.data.remote.register.datasource.RegisterRemoteDataSource
import ch.app.hk.bank.locator.feature.auth.data.remote.register.response.RegisterResponse
import ch.app.hk.bank.locator.feature.auth.data.repo.register.model.RegisterErrorCode
import ch.app.hk.bank.locator.feature.auth.data.repo.register.model.RegisterResult
import ch.app.library.hiltext.annotation.HiltExtBindModule
import javax.inject.Inject

@HiltExtBindModule
class RegisterRepositoryImpl @Inject constructor(
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
            is RegisterResponse.Error -> {
                when (RegisterErrorCode.fromString(response.code)) {
                    RegisterErrorCode.ERROR_UNKNOWN -> RegisterResult.Error.Unknown
                    RegisterErrorCode.ERROR_INVALID_EMAIL -> RegisterResult.Error.Register.InvalidEmail
                    RegisterErrorCode.ERROR_WEAK_PASSWORD -> RegisterResult.Error.Register.WeakPassword
                    RegisterErrorCode.ERROR_EMAIL_ALREADY_IN_USE -> RegisterResult.Error.Register.EmailAlreadyInUse
                }
            }

            is RegisterResponse.Success -> RegisterResult.Authorized
        }
    }
}
