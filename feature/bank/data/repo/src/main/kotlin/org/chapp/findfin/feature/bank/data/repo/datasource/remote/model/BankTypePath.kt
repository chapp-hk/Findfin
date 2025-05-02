package org.chapp.findfin.feature.bank.data.repo.datasource.remote.model

internal const val PATH_ATM = "banks-atm-locator"
internal const val PATH_BRANCH = "banks-branch-locator"

enum class TypePath(val value: String) {
    ATM(PATH_ATM),
    BRANCH(PATH_BRANCH),
}
