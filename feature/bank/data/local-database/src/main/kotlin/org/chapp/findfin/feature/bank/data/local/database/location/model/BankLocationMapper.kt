package org.chapp.findfin.feature.bank.data.local.database.location.model

import org.chapp.findfin.feature.bank.data.repo.local.model.BankLocationLocal
import org.mapstruct.Mapper

@Mapper
internal interface BankLocationMapper {
    fun clone(locator: BankLocationLocal): BankLocationEntity

    fun toLocalModel(locator: BankLocationEntity): BankLocationLocal
}
