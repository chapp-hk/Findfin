package ch.app.hk.bank.locator.feature.locator.data.repo.model

import ch.app.hk.bank.locator.feature.locator.data.remote.api.LocatorPath

internal fun Locator.toRemoteLocatorPath(): LocatorPath {
    return when (this) {
        Locator.ATM -> LocatorPath.ATM
        Locator.BRANCH -> LocatorPath.BRANCH
    }
}

internal fun String.toApiLang(): String {
    return when (this.lowercase()) {
        "zh" -> "tc"
        else -> "en"
    }
}
