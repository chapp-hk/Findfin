package org.chapp.findfin.feature.bank.data.repo.location.mapper

import io.kotest.matchers.shouldBe
import org.chapp.findfin.feature.bank.data.remote.location.api.LocationPath
import org.chapp.findfin.feature.bank.data.remote.location.model.BankLocationResponse
import org.chapp.findfin.feature.bank.data.repo.location.local.model.BankLocationLocal
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.EnumSource
import org.mapstruct.factory.Mappers

@DisplayName("BankLocationMapper unit tests")
class BankLocationMapperTest {
    private val bankLocationMapper = Mappers.getMapper(BankLocationMapper::class.java)

    @ParameterizedTest(
        name = "When type is {0}, target BankLocal should have {0} as type",
    )
    @EnumSource(LocationPath::class)
    fun testConvertToLocal(input: LocationPath) {
        val bankLocationResponse =
            BankLocationResponse(
                district = "mock district",
                bankName = "mock bank name",
                typeName = "bank",
                address = "mock address",
                serviceHours = "24 hours",
                latitude = 0.0,
                longitude = 0.0,
            )

        bankLocationMapper.convertToLocal(
            type = input,
            locator = bankLocationResponse,
        ) shouldBe
            BankLocationLocal(
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
