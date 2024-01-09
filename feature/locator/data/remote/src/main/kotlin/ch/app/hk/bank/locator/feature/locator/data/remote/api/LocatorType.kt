package ch.app.hk.bank.locator.feature.locator.data.remote.api

internal const val PATH_ATM = "banks-atm-locator"
internal const val PATH_BRANCH = "banks-branch-locator"

enum class LocatorType(val value: String) {
    ATM(PATH_ATM),
    BRANCH(PATH_BRANCH),
}
