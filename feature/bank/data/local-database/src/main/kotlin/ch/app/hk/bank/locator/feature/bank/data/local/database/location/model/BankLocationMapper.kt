package ch.app.hk.bank.locator.feature.bank.data.local.database.location.model

import ch.app.hk.bank.locator.feature.bank.data.repo.location.local.model.BankLocationLocal
import org.mapstruct.Mapper

@Mapper
internal interface BankLocationMapper {
    fun clone(locator: BankLocationLocal): BankLocationEntity

    fun toLocalModel(locator: BankLocationEntity): BankLocationLocal
}
