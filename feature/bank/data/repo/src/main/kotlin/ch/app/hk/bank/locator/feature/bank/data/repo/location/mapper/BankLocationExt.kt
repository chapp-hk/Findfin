package ch.app.hk.bank.locator.feature.bank.data.repo.location.mapper

import ch.app.hk.bank.locator.feature.bank.data.remote.location.api.LocatorPath
import ch.app.hk.bank.locator.feature.bank.data.repo.location.model.BankLocationType

internal fun BankLocationType.toRemoteLocatorPath(): LocatorPath {
    return when (this) {
        BankLocationType.ATM -> LocatorPath.ATM
        BankLocationType.BRANCH -> LocatorPath.BRANCH
    }
}

internal fun String.toApiLang(): String {
    return when (this.lowercase()) {
        "zh" -> "tc"
        else -> "en"
    }
}
