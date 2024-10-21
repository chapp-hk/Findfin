package org.chapp.findfin.feature.auth.data.repo.login.model

/**
 * Sealed interface representing the result of a login attempt.
 */
sealed interface LoginResult {
    /**
     * Represents a successful login attempt.
     */
    data object Success : LoginResult

    /**
     * Sealed interface representing different types of login errors.
     */
    sealed interface Error : LoginResult {
        /**
         * Represents an unknown error during login.
         */
        data object Unknown : Error

        /**
         * Represents an error due to invalid credentials.
         */
        data object InvalidCredential : Error

        /**
         * Represents an error due to a disabled account.
         */
        data object AccountDisabled : Error

        /**
         * Represents an error due to too many login attempts.
         */
        data object TooManyRequest : Error
    }
}
