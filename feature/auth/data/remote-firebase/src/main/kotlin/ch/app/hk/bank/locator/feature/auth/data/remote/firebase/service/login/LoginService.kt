package ch.app.hk.bank.locator.feature.auth.data.remote.firebase.service.login

import ch.app.framework.hiltext.annotation.HiltExtBindModule
import ch.app.hk.bank.locator.core.threading.DispatcherIo
import ch.app.hk.bank.locator.feature.auth.data.remote.login.datasource.LoginRemoteDataSource
import ch.app.hk.bank.locator.feature.auth.data.remote.login.response.LoginResponse
import com.google.firebase.FirebaseTooManyRequestsException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthInvalidUserException
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltExtBindModule
class LoginService
    @Inject
    constructor(
        @DispatcherIo private val ioDispatcher: CoroutineDispatcher,
        private val firebaseAuth: FirebaseAuth,
    ) : LoginRemoteDataSource {
        override suspend fun emailPasswordLogin(
            email: String,
            password: String,
        ): LoginResponse {
            return withContext(ioDispatcher) {
                runCatching {
                    firebaseAuth.signInWithEmailAndPassword(email, password).await()
                    LoginResponse.Success
                }.getOrElse { error ->
                    when (error) {
                        is FirebaseAuthInvalidCredentialsException ->
                            LoginResponse.Error.InvalidCredential

                        is FirebaseAuthInvalidUserException ->
                            LoginResponse.Error.AccountDisabled

                        is FirebaseTooManyRequestsException -> {
                            LoginResponse.Error.TooManyRequest
                        }

                        else -> LoginResponse.Error.Unknown
                    }
                }
            }
        }
    }
