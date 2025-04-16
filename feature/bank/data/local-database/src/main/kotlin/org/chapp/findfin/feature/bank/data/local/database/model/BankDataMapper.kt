package org.chapp.findfin.feature.bank.data.local.database.model

import org.chapp.findfin.feature.bank.data.repo.local.model.BankLocal
import org.mapstruct.Mapper

@Mapper
internal interface BankDataMapper {
    fun clone(locator: BankLocal): BankEntity

    fun toLocalModel(locator: BankEntity): BankLocal
}
