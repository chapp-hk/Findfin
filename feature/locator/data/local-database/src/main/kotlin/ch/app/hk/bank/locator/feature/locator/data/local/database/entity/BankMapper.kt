package ch.app.hk.bank.locator.feature.locator.data.local.database.entity

import ch.app.hk.bank.locator.feature.locator.data.local.entity.BankLocal
import org.mapstruct.Mapper

@Mapper
interface BankMapper {
    fun clone(bank: BankLocal): BankEntity
}
