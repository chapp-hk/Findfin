package org.chapp.findfin.feature.auth.data.remote.firebase.service.user

import com.google.firebase.auth.FirebaseAuth
import org.chapp.findfin.feature.auth.data.repo.user.remote.datasource.UserRemoteDataSource
import org.chapp.findfin.feature.auth.data.repo.user.remote.response.UserResponse
import org.chapp.library.hiltwrap.annotation.HiltWrapBindModule
import javax.inject.Inject

@HiltWrapBindModule
internal class UserService @Inject constructor(
    private val firebaseAuth: FirebaseAuth,
) : UserRemoteDataSource {
    override suspend fun getCurrentUser(): UserResponse? {
        val mapper = UserResponseMapper()
        return firebaseAuth.currentUser?.let(mapper::toUserResponse)
    }
}
