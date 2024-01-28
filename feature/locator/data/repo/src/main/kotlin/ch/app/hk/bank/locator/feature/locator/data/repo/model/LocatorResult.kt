package ch.app.hk.bank.locator.feature.locator.data.repo.model

sealed interface LocatorResult {
    data object HasNext : LocatorResult

    data object End : LocatorResult

    data class Error(val cause: Throwable) : LocatorResult
}
