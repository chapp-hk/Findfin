package org.chapp.findfin.feature.auth.data.remote.firebase.service.register

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import com.google.firebase.auth.FirebaseAuthWeakPasswordException
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import org.chapp.findfin.core.threading.DispatcherIo
import org.chapp.findfin.feature.auth.data.repo.register.remote.datasource.RegisterRemoteDataSource
import org.chapp.findfin.feature.auth.data.repo.register.remote.response.RegisterResponse
import org.chapp.library.hiltwrap.annotation.HiltWrapBindModule
import javax.inject.Inject

@HiltWrapBindModule
internal class RegisterService @Inject constructor(
    @DispatcherIo private val ioDispatcher: CoroutineDispatcher,
    private val firebaseAuth: FirebaseAuth,
) : RegisterRemoteDataSource {
    override suspend fun emailPasswordRegister(
        email: String,
        password: String,
    ): RegisterResponse {
        return withContext(ioDispatcher) {
            runCatching {
                firebaseAuth.createUserWithEmailAndPassword(email, password).await()
                RegisterResponse.Success
            }.getOrElse { error ->
                when (error) {
                    is FirebaseAuthWeakPasswordException ->
                        RegisterResponse.Error.WeakPassword

                    is FirebaseAuthInvalidCredentialsException ->
                        RegisterResponse.Error.InvalidEmail

                    is FirebaseAuthUserCollisionException ->
                        RegisterResponse.Error.UserCollision

                    else -> RegisterResponse.Error.Unknown
                }
            }
        }
    }
}
