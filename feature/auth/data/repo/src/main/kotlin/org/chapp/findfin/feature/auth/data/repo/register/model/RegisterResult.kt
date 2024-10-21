package org.chapp.findfin.feature.auth.data.repo.register.model

/**
 * Sealed interface representing the result of a registration attempt.
 */
sealed interface RegisterResult {
    /**
     * Represents a successful registration attempt.
     */
    data object Authorized : RegisterResult

    /**
     * Sealed interface representing different types of registration errors.
     */
    sealed interface Error : RegisterResult {
        /**
         * Represents an unknown error during registration.
         */
        data object Unknown : Error

        /**
         * Represents an error due to an invalid email.
         */
        data object InvalidEmail : Error

        /**
         * Represents an error due to a weak password.
         */
        data object WeakPassword : Error

        /**
         * Represents an error due to the email already being in use.
         */
        data object EmailAlreadyInUse : Error
    }
}
