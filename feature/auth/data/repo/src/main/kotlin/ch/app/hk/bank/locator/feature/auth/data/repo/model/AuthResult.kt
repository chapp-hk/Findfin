package ch.app.hk.bank.locator.feature.auth.data.repo.model

sealed interface AuthResult {
    data object Authorized : AuthResult

    data class Failed(val message: String) : AuthResult
}
