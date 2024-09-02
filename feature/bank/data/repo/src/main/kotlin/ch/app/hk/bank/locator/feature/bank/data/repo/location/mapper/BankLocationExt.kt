package ch.app.hk.bank.locator.feature.bank.data.repo.location.mapper

import ch.app.hk.bank.locator.feature.bank.data.remote.location.api.LocationPath
import ch.app.hk.bank.locator.feature.bank.data.repo.location.model.BankLocationType

internal fun BankLocationType.toRemoteLocationPath(): LocationPath {
    return when (this) {
        BankLocationType.ATM -> LocationPath.ATM
        BankLocationType.BRANCH -> LocationPath.BRANCH
    }
}

internal fun String.toApiLang(): String {
    return when (this.lowercase()) {
        "zh" -> "tc"
        else -> "en"
    }
}
