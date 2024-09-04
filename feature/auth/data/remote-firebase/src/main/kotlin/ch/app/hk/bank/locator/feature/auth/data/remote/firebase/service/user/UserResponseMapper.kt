package ch.app.hk.bank.locator.feature.auth.data.remote.firebase.service.user

import ch.app.hk.bank.locator.feature.auth.data.repo.user.remote.response.UserResponse
import com.google.firebase.auth.FirebaseUser

class UserResponseMapper {
    fun toUserResponse(user: FirebaseUser): UserResponse {
        return UserResponse(
            displayName = user.displayName.orEmpty(),
            email = user.email.orEmpty(),
            isEmailVerified = user.isEmailVerified,
        )
    }
}
