package org.chapp.findfin.feature.bank.data.repo.mapper

import org.chapp.findfin.feature.bank.data.remote.network.api.TypePath
import org.chapp.findfin.feature.bank.data.repo.model.BankType

internal fun BankType.toRemoteLocationPath(): TypePath {
    return when (this) {
        BankType.ATM -> TypePath.ATM
        BankType.BRANCH -> TypePath.BRANCH
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
