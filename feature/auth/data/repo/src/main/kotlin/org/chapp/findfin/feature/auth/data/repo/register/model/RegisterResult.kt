package org.chapp.findfin.feature.auth.data.repo.register.model

sealed interface RegisterResult {
    data object Authorized : RegisterResult

    sealed interface Error : RegisterResult {
        data object Unknown : Error

        sealed interface Register : Error {
            data object InvalidEmail : Register

            data object WeakPassword : Register

            data object EmailAlreadyInUse : Register
        }
    }
}
