package ch.app.hk.bank.locator.feature.auth.data.repo.login.remote.response

sealed interface LoginResponse {
    data object Success : LoginResponse

    sealed interface Error : LoginResponse {
        data object Unknown : Error

        data object InvalidCredential : Error

        data object AccountDisabled : Error

        data object TooManyRequest : Error
    }
}
