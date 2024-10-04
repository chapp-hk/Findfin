package org.chapp.findfin.feature.auth.data.remote.firebase.service.user

import com.google.firebase.auth.FirebaseUser
import org.chapp.findfin.feature.auth.data.repo.user.remote.response.UserResponse

internal class UserResponseMapper {
    fun toUserResponse(user: FirebaseUser): UserResponse {
        return UserResponse(
            displayName = user.displayName.orEmpty(),
            email = user.email.orEmpty(),
            isEmailVerified = user.isEmailVerified,
        )
    }
}
