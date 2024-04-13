package ch.app.hk.bank.locator.feature.auth.data.remote.firebase.service.register

import ch.app.framework.hiltext.annotation.HiltExtBindModule
import ch.app.hk.bank.locator.core.threading.DispatcherIo
import ch.app.hk.bank.locator.feature.auth.data.remote.register.datasource.RegisterRemoteDataSource
import ch.app.hk.bank.locator.feature.auth.data.remote.register.response.RegisterResponse
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthException
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltExtBindModule
internal class RegisterService
    @Inject
    constructor(
        @DispatcherIo private val ioDispatcher: CoroutineDispatcher,
        private val firebaseAuth: FirebaseAuth,
    ) : RegisterRemoteDataSource {
        override fun isAuthorized(): Boolean {
            return firebaseAuth.currentUser != null
        }

        override suspend fun anonymousLogin(): RegisterResponse {
            return withContext(ioDispatcher) {
                runCatching {
                    val auth = firebaseAuth.signInAnonymously().await()
                    RegisterResponse.Success(auth.user!!.isAnonymous)
                }.getOrElse {
                    RegisterResponse.Error(
                        code = "",
                        message = "",
                    )
                }
            }
        }

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
