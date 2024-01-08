package ch.app.hk.bank.locator.feature.locator.data.repo.model

import ch.app.hk.bank.locator.feature.locator.data.local.entity.BankLocal
import ch.app.hk.bank.locator.feature.locator.data.remote.datasource.LocatorRemoteDataSource
import ch.app.hk.bank.locator.feature.locator.data.remote.response.Bank
import org.mapstruct.Mapper
import org.mapstruct.Mapping

@Mapper
interface LocatorMapper {
    @Mapping(
        source = "type",
        target = "type",
    )
    fun convertToLocal(
        type: LocatorRemoteDataSource.Type,
        bank: Bank,
    ): BankLocal
}
