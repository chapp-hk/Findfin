package ch.app.hk.bank.locator.feature.locator.data.repo.mapper

sealed interface LocatorFetchResult {
    data object HasNext : LocatorFetchResult

    data object End : LocatorFetchResult

    data class Error(val cause: Throwable) : LocatorFetchResult
}
