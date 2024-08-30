package ch.app.hk.bank.locator.feature.bank.data.repo.mapper

import ch.app.hk.bank.locator.feature.bank.data.local.model.LocatorLocal
import ch.app.hk.bank.locator.feature.bank.data.remote.api.LocatorPath
import ch.app.hk.bank.locator.feature.bank.data.remote.model.LocatorResponse
import ch.app.hk.bank.locator.feature.bank.data.repo.model.LocatorModel
import org.mapstruct.Mapper
import org.mapstruct.Mapping

@Mapper
interface LocatorMapper {
    @Mapping(
        source = "type",
        target = "type",
    )
    fun convertToLocal(
        type: LocatorPath,
        locator: LocatorResponse,
    ): LocatorLocal

    fun convertToDataModel(locator: LocatorLocal): LocatorModel
}
