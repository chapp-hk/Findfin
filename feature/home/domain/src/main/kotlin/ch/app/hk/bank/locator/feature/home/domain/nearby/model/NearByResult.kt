package ch.app.hk.bank.locator.feature.home.domain.nearby.model

sealed interface NearByResult {
    data object UnknownError : NearByResult

    data class Location(val list: List<Service>) : NearByResult
}
