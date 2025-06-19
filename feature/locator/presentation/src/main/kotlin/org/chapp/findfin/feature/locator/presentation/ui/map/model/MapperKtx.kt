package org.chapp.findfin.feature.locator.presentation.ui.map.model

import org.chapp.findfin.feature.bank.data.repo.model.BankType
import org.chapp.findfin.feature.locator.presentation.navigation.MapSearchType

fun MapSearchType?.toBankType(): BankType? {
    return when (this) {
        MapSearchType.BRANCH -> BankType.BRANCH
        MapSearchType.ATM -> BankType.ATM
        null -> null
    }
}
