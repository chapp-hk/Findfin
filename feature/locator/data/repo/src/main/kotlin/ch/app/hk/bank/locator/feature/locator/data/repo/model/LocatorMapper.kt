package ch.app.hk.bank.locator.feature.locator.data.repo.model

import ch.app.hk.bank.locator.feature.locator.data.local.entity.BankLocal
import ch.app.hk.bank.locator.feature.locator.data.remote.api.LocatorType
import ch.app.hk.bank.locator.feature.locator.data.remote.response.LocatorResponse
import org.mapstruct.Mapper
import org.mapstruct.Mapping

@Mapper
interface LocatorMapper {
    @Mapping(
        source = "type",
        target = "type",
    )
    fun convertToLocal(
        type: LocatorType,
        locator: LocatorResponse,
    ): BankLocal
}
