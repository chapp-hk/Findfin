package ch.app.hk.bank.locator.feature.home.domain.nearby.mapper

import ch.app.hk.bank.locator.feature.bank.data.repo.location.model.BankLocationModel
import ch.app.hk.bank.locator.feature.home.domain.nearby.model.Service
import org.mapstruct.Mapper

@Mapper
interface ServiceMapper {
    fun clone(locator: BankLocationModel): Service
}
