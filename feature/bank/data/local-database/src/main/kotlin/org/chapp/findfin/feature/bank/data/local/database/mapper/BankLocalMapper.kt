package org.chapp.findfin.feature.bank.data.local.database.mapper

import org.chapp.findfin.feature.bank.data.local.database.model.BankEntity
import org.chapp.findfin.feature.bank.data.repo.datasource.local.model.BankLocal
import org.mapstruct.Mapper

@Mapper
internal interface BankLocalMapper {
    fun toDatabaseEntity(local: BankLocal): BankEntity

    fun toLocalModel(entity: BankEntity): BankLocal
}
