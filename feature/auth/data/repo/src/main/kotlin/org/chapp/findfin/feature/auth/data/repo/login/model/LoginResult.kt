package org.chapp.findfin.feature.auth.data.repo.login.model

sealed interface LoginResult {
    data object Success : LoginResult

    sealed interface Error : LoginResult {
        data object Unknown : Error

        data object InvalidCredential : Error

        data object AccountDisabled : Error

        data object TooManyRequest : Error
    }
}
