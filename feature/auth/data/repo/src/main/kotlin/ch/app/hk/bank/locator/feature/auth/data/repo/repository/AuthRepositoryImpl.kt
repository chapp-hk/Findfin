package ch.app.hk.bank.locator.feature.auth.data.repo.repository

import ch.app.framework.hiltext.annotation.HiltExtBindModule
import ch.app.hk.bank.locator.feature.auth.data.remote.datasource.AuthRemoteDataSource
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
            return runCatching {
                authRemoteDataSource.anonymousLogin().getOrThrow()
                AuthResult.Authorized
            }.getOrElse { error ->
                AuthResult.Failed(error.localizedMessage)
            }
        }
    }
