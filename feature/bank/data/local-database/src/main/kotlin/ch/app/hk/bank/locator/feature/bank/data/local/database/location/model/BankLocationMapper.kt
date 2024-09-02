package ch.app.hk.bank.locator.feature.bank.data.local.database.location.model

import ch.app.hk.bank.locator.feature.bank.data.local.bank.model.BankLocationLocal
import org.mapstruct.Mapper

@Mapper
interface BankLocationMapper {
    fun clone(locator: BankLocationLocal): BankLocationEntity

    fun toLocalModel(locator: BankLocationEntity): BankLocationLocal
}
