package ch.app.hk.bank.locator.feature.bank.data.repo.mapper

import ch.app.hk.bank.locator.feature.bank.data.remote.api.LocatorPath
import ch.app.hk.bank.locator.feature.bank.data.repo.model.LocatorType

internal fun LocatorType.toRemoteLocatorPath(): LocatorPath {
    return when (this) {
        LocatorType.ATM -> LocatorPath.ATM
        LocatorType.BRANCH -> LocatorPath.BRANCH
    }
}

internal fun String.toApiLang(): String {
    return when (this.lowercase()) {
        "zh" -> "tc"
        else -> "en"
    }
}
