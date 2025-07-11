package org.chapp.findfin.feature.auth.data.remote.firebase.service.login

import com.google.firebase.FirebaseTooManyRequestsException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthInvalidUserException
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import org.chapp.findfin.core.threading.DispatcherIo
import org.chapp.findfin.feature.auth.data.repo.login.remote.datasource.LoginRemoteDataSource
import org.chapp.findfin.feature.auth.data.repo.login.remote.response.LoginResponse
import org.chapp.library.hiltwrap.annotation.HiltWrapBindModule
import javax.inject.Inject

@HiltWrapBindModule
internal class LoginService @Inject constructor(
    @param:DispatcherIo private val ioDispatcher: CoroutineDispatcher,
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
