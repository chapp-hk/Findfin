package ch.app.hk.bank.locator.feature.bank.data.remote.location.model

sealed interface LocationResult {
    data class Success(val data: List<BankLocationResponse>) : LocationResult

    data object Error : LocationResult
}
