package org.chapp.findfin.feature.home.domain.nearby.mapper

import org.chapp.findfin.feature.bank.data.repo.model.BankLocationModel
import org.chapp.findfin.feature.home.domain.nearby.model.Service
import org.mapstruct.Mapper

@Mapper
interface ServiceMapper {
    fun clone(locator: BankLocationModel): Service
}
