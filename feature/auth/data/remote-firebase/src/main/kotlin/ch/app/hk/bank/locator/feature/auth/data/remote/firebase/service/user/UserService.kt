package ch.app.hk.bank.locator.feature.auth.data.remote.firebase.service.user

import ch.app.hk.bank.locator.core.threading.DispatcherIo
import ch.app.hk.bank.locator.feature.auth.data.repo.user.remote.datasource.UserRemoteDataSource
import ch.app.hk.bank.locator.feature.auth.data.repo.user.remote.response.UserResponse
import ch.app.library.hiltwrap.annotation.HiltWrapBindModule
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltWrapBindModule
internal class UserService @Inject constructor(
    @DispatcherIo private val ioDispatcher: CoroutineDispatcher,
    private val firebaseAuth: FirebaseAuth,
) : UserRemoteDataSource {
    override suspend fun getCurrentUser(): UserResponse? {
        val mapper = UserResponseMapper()

        return withContext(ioDispatcher) {
            firebaseAuth.currentUser?.let(mapper::toUserResponse)
        }
    }
}
