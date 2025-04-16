package org.chapp.findfin.feature.bank.data.remote.network.api

internal const val PATH_ATM = "banks-atm-locator"
internal const val PATH_BRANCH = "banks-branch-locator"

enum class LocationPath(val value: String) {
    ATM(PATH_ATM),
    BRANCH(PATH_BRANCH),
}
