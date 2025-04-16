package org.chapp.findfin.feature.bank.data.repo.mapper

import org.chapp.findfin.feature.bank.data.remote.network.api.LocationPath
import org.chapp.findfin.feature.bank.data.repo.model.BankLocationType

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

internal fun String.toLocalLanguage(): String {
    val availableLanguages = listOf("en", "zh")
    return if (availableLanguages.contains(this)) {
        this
    } else {
        "en"
    }
}
