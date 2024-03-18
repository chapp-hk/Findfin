package ch.app.hk.bank.locator.feature.auth.data.repo.repository

import ch.app.framework.hiltext.annotation.HiltExtBindModule
import ch.app.hk.bank.locator.feature.auth.data.remote.datasource.AuthRemoteDataSource
import ch.app.hk.bank.locator.feature.auth.data.remote.response.AuthResponse
import ch.app.hk.bank.locator.feature.auth.data.repo.model.AuthErrorCode
import ch.app.hk.bank.locator.feature.auth.data.repo.model.AuthResult
import javax.inject.Inject

@HiltExtBindModule
class AuthRepositoryImpl
    @Inject
    constructor(
        private val authRemoteDataSource: AuthRemoteDataSource,
    ) : AuthRepository {
        override fun isAuthorized(): Boolean {
            return authRemoteDataSource.isAuthorized()
        }

        override suspend fun anonymousLogin(): AuthResult {
            return when (authRemoteDataSource.anonymousLogin()) {
                is AuthResponse.Error -> {
                    AuthResult.Error.Unknown
                }
                is AuthResponse.Success -> {
                    AuthResult.Authorized
                }
            }
        }

        override suspend fun emailPasswordRegister(
            email: String,
            password: String,
        ): AuthResult {
            val response =
                authRemoteDataSource.emailPasswordRegister(
                    email = email,
                    password = password,
                )

            return when (response) {
                is AuthResponse.Error -> {
                    when (AuthErrorCode.fromString(response.code)) {
                        AuthErrorCode.ERROR_UNKNOWN -> AuthResult.Error.Unknown
                        AuthErrorCode.ERROR_INVALID_EMAIL -> AuthResult.Error.Register.InvalidEmail
                        AuthErrorCode.ERROR_WEAK_PASSWORD -> AuthResult.Error.Register.WeakPassword
                        AuthErrorCode.ERROR_EMAIL_ALREADY_IN_USE -> AuthResult.Error.Register.EmailAlreadyInUse
                    }
                }
                is AuthResponse.Success -> AuthResult.Authorized
            }
        }
    }
