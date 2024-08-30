package ch.app.hk.bank.locator.feature.bank.data.local.database.model

import ch.app.hk.bank.locator.feature.bank.data.local.model.LocatorLocal
import org.mapstruct.Mapper

@Mapper
interface LocatorMapper {
    fun clone(locator: LocatorLocal): LocatorEntity

    fun toLocalModel(locator: LocatorEntity): LocatorLocal
}
