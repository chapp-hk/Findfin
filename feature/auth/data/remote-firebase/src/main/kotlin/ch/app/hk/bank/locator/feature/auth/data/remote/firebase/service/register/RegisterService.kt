package ch.app.hk.bank.locator.feature.auth.data.remote.firebase.service.register

import ch.app.hk.bank.locator.core.threading.DispatcherIo
import ch.app.hk.bank.locator.feature.auth.data.repo.register.remote.datasource.RegisterRemoteDataSource
import ch.app.hk.bank.locator.feature.auth.data.repo.register.remote.response.RegisterResponse
import ch.app.library.hiltwrap.annotation.HiltWrapBindModule
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthException
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
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
                RegisterResponse.Success(isAnonymous = false)
            }.getOrElse { error ->
                val errorCode =
                    if (error is FirebaseAuthException) {
                        error.errorCode
                    } else {
                        ""
                    }

                RegisterResponse.Error(
                    code = errorCode,
                    message = "",
                )
            }
        }
    }
}
