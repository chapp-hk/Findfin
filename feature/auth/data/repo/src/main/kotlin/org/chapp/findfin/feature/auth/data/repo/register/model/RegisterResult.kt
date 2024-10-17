package org.chapp.findfin.feature.auth.data.repo.register.model

sealed interface RegisterResult {
    data object Authorized : RegisterResult

    sealed interface Error : RegisterResult {
        data object Unknown : Error

        data object InvalidEmail : Error

        data object WeakPassword : Error

        data object EmailAlreadyInUse : Error
    }
}
