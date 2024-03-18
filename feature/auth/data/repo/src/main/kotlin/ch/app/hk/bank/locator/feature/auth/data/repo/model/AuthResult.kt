package ch.app.hk.bank.locator.feature.auth.data.repo.model

sealed interface AuthResult {
    data object Authorized : AuthResult

    sealed interface Error : AuthResult {
        data object Unknown : Error

        sealed interface Register : Error {
            data object InvalidEmail : Register

            data object WeakPassword : Register

            data object EmailAlreadyInUse : Register
        }
    }
}
