package ch.app.hk.bank.locator.feature.bank.data.repo.location.mapper

import ch.app.hk.bank.locator.feature.bank.data.remote.location.api.LocationPath
import ch.app.hk.bank.locator.feature.bank.data.remote.location.model.BankLocationResponse
import ch.app.hk.bank.locator.feature.bank.data.repo.location.local.model.BankLocationLocal
import ch.app.hk.bank.locator.feature.bank.data.repo.location.model.BankLocationModel
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
