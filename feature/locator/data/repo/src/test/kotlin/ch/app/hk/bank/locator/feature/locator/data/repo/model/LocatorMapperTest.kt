package ch.app.hk.bank.locator.feature.locator.data.repo.model

import ch.app.hk.bank.locator.feature.locator.data.local.entity.BankLocal
import ch.app.hk.bank.locator.feature.locator.data.remote.datasource.LocatorRemoteDataSource
import ch.app.hk.bank.locator.feature.locator.data.remote.response.Bank
import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.EnumSource
import org.mapstruct.factory.Mappers

@DisplayName("LocatorMapper unit tests")
class LocatorMapperTest {
    private val locatorMapper = Mappers.getMapper(LocatorMapper::class.java)

    @ParameterizedTest(
        name = "When type is {0}, target BankLocal should have {0} as type",
    )
    @EnumSource(LocatorRemoteDataSource.Type::class)
    fun testConvertToLocal(input: LocatorRemoteDataSource.Type) {
        val bank =
            Bank(
                district = "mock district",
                bankName = "mock bank name",
                typeName = "bank",
                address = "mock address",
                serviceHours = "24 hours",
                latitude = 0.0,
                longitude = 0.0,
            )

        locatorMapper.convertToLocal(
            type = input,
            bank = bank,
        ) shouldBe
            BankLocal(
                type = input.name,
                district = "mock district",
                bankName = "mock bank name",
                typeName = "bank",
                address = "mock address",
                serviceHours = "24 hours",
                latitude = 0.0,
                longitude = 0.0,
            )
    }
}
