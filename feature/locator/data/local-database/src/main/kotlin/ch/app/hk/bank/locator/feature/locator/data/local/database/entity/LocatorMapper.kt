package ch.app.hk.bank.locator.feature.locator.data.local.database.entity

import ch.app.hk.bank.locator.feature.locator.data.local.entity.LocatorLocal
import org.mapstruct.Mapper

@Mapper
interface LocatorMapper {
    fun clone(locator: LocatorLocal): LocatorEntity
}
