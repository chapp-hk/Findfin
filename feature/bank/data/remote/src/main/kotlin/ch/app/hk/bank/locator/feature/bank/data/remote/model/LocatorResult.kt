package ch.app.hk.bank.locator.feature.bank.data.remote.model

sealed interface LocatorResult {
    data class Success(val data: List<LocatorResponse>) : LocatorResult

    data object Error : LocatorResult
}
