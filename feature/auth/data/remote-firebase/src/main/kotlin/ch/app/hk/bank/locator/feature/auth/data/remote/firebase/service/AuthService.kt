package ch.app.hk.bank.locator.feature.auth.data.remote.firebase.service

import ch.app.framework.hiltext.annotation.HiltExtBindModule
import ch.app.hk.bank.locator.core.threading.DispatcherIo
import ch.app.hk.bank.locator.feature.auth.data.remote.datasource.AuthRemoteDataSource
import ch.app.hk.bank.locator.feature.auth.data.remote.response.AuthResponse
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthException
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltExtBindModule
internal class AuthService
    @Inject
    constructor(
        @DispatcherIo private val ioDispatcher: CoroutineDispatcher,
        private val firebaseAuth: FirebaseAuth,
    ) : AuthRemoteDataSource {
        override fun isAuthorized(): Boolean {
            return firebaseAuth.currentUser != null
        }

        override suspend fun anonymousLogin(): AuthResponse {
            return withContext(ioDispatcher) {
                runCatching {
                    val auth = firebaseAuth.signInAnonymously().await()
                    AuthResponse.Success(auth.user!!.isAnonymous)
                }.getOrElse {
                    AuthResponse.Error(
                        code = "",
                        message = "",
                    )
                }
            }
        }

        // register test:
        // - register success
        // - register with invalid email
        //   - FirebaseAuthInvalidCredentialsException: ERROR_INVALID_EMAIL - The email address is badly formatted.
        // - register with short password
        //   - FirebaseAuthWeakPasswordException: ERROR_WEAK_PASSWORD - The given password is invalid. [ Password should be at least 6 characters ]
        // - register with existing email
        //   - FirebaseAuthUserCollisionException: ERROR_EMAIL_ALREADY_IN_USE - The email address is already in use by another account.

        override suspend fun emailPasswordRegister(
            email: String,
            password: String,
        ): AuthResponse {
            return withContext(ioDispatcher) {
                runCatching {
                    firebaseAuth.createUserWithEmailAndPassword(email, password).await()
                    AuthResponse.Success(isAnonymous = false)
                }.getOrElse { error ->
                    val errorCode =
                        if (error is FirebaseAuthException) {
                            error.errorCode
                        } else {
                            ""
                        }

                    AuthResponse.Error(
                        code = errorCode,
                        message = "",
                    )
                }
            }
        }
    }
