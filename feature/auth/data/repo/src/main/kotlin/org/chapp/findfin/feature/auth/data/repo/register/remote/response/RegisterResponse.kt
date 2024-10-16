package org.chapp.findfin.feature.auth.data.repo.register.remote.response

sealed interface RegisterResponse {
    data object Success : RegisterResponse

    sealed interface Error : RegisterResponse {
        data object Unknown : Error

        data object InvalidEmail : Error

        data object WeakPassword : Error

        data object UserCollision : Error
    }
}
