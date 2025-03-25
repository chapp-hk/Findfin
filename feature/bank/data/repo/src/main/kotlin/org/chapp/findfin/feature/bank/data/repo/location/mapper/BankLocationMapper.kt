package org.chapp.findfin.feature.bank.data.repo.location.mapper

import org.chapp.findfin.feature.bank.data.remote.location.api.LocationPath
import org.chapp.findfin.feature.bank.data.remote.location.model.BankLocationResponse
import org.chapp.findfin.feature.bank.data.repo.location.local.model.BankLocationLocal
import org.chapp.findfin.feature.bank.data.repo.location.model.BankLocationModel
import org.mapstruct.Mapper
import org.mapstruct.Mapping
import org.mapstruct.Mappings

@Mapper
interface BankLocationMapper {
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
    ): BankLocationLocal

    fun convertToDataModel(locator: BankLocationLocal): BankLocationModel
}
