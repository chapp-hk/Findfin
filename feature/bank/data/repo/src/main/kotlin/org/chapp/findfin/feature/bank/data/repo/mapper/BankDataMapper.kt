package org.chapp.findfin.feature.bank.data.repo.mapper

import org.chapp.findfin.feature.bank.data.remote.network.api.LocationPath
import org.chapp.findfin.feature.bank.data.remote.network.model.BankLocationResponse
import org.chapp.findfin.feature.bank.data.repo.local.model.BankLocal
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
        type: LocationPath,
        locator: BankLocationResponse,
    ): BankLocal

    fun convertToDataModel(locator: BankLocal): BankModel
}
