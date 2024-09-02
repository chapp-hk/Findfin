package ch.app.hk.bank.locator.feature.bank.data.remote.location.model

sealed interface LocatorResult {
    data class Success(val data: List<BankLocationResponse>) : LocatorResult

    data object Error : LocatorResult
}
