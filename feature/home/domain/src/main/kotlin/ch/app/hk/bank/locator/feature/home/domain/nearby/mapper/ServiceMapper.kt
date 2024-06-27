package ch.app.hk.bank.locator.feature.home.domain.nearby.mapper

import ch.app.hk.bank.locator.feature.home.domain.nearby.model.Service
import ch.app.hk.bank.locator.feature.locator.data.repo.model.LocatorModel
import org.mapstruct.Mapper

@Mapper
interface ServiceMapper {
    fun clone(locator: LocatorModel): Service
}
