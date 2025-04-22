package org.chapp.findfin.feature.bank.data.repo.mapper

import org.chapp.findfin.feature.bank.data.repo.datasource.local.model.BankLocal
import org.chapp.findfin.feature.bank.data.repo.datasource.remote.model.BankRemote
import org.chapp.findfin.feature.bank.data.repo.datasource.remote.model.TypePath
import org.chapp.findfin.feature.bank.data.repo.model.BankModel
import org.mapstruct.Mapper
import org.mapstruct.Mapping
import org.mapstruct.Mappings

@Mapper
interface BankDataMapper {
    @Mappings(
        Mapping(
            source = "type",
            target = "type",
        ),
        Mapping(
            source = "language",
            target = "language",
        ),
    )
    fun convertToLocal(
        language: String,
        type: TypePath,
        bankRemote: BankRemote,
    ): BankLocal

    fun convertToDataModel(local: BankLocal): BankModel
}
