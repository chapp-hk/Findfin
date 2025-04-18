package org.chapp.findfin.feature.bank.data.local.database.model

import org.chapp.findfin.feature.bank.data.repo.datasource.local.model.BankLocal
import org.mapstruct.Mapper

@Mapper
internal interface BankDataMapper {
    fun clone(local: BankLocal): BankEntity

    fun toLocalModel(entity: BankEntity): BankLocal
}
