package ch.app.hk.bank.locator.feature.bank.data.repo.location.mapper

import ch.app.hk.bank.locator.feature.bank.data.local.bank.model.BankLocationLocal
import ch.app.hk.bank.locator.feature.bank.data.remote.location.api.LocatorPath
import ch.app.hk.bank.locator.feature.bank.data.remote.location.model.BankLocationResponse
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
        type: LocatorPath,
        locator: BankLocationResponse,
    ): BankLocationLocal

    fun convertToDataModel(locator: BankLocationLocal): BankLocationModel
}
