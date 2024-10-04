package org.chapp.findfin.feature.bank.data.repo.location.mapper

import org.chapp.findfin.feature.bank.data.remote.location.api.LocationPath
import org.chapp.findfin.feature.bank.data.remote.location.model.BankLocationResponse
import org.chapp.findfin.feature.bank.data.repo.location.local.model.BankLocationLocal
import org.chapp.findfin.feature.bank.data.repo.location.model.BankLocationModel
import org.mapstruct.Mapper
import org.mapstruct.Mapping

@Mapper
interface BankLocationMapper {
    @Mapping(
        source = "type",
        target = "type",
    )
    fun convertToLocal(
        type: LocationPath,
        locator: BankLocationResponse,
    ): BankLocationLocal

    fun convertToDataModel(locator: BankLocationLocal): BankLocationModel
}
