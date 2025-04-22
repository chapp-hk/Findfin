package org.chapp.findfin.feature.bank.data.repo.mapper

import io.kotest.matchers.shouldBe
import org.chapp.findfin.feature.bank.data.repo.datasource.local.model.BankLocal
import org.chapp.findfin.feature.bank.data.repo.datasource.remote.model.BankRemote
import org.chapp.findfin.feature.bank.data.repo.datasource.remote.model.TypePath
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.EnumSource
import org.mapstruct.factory.Mappers

@DisplayName("BankDataMapper unit tests")
class BankDataMapperTest {
    private val bankDataMapper = Mappers.getMapper(BankDataMapper::class.java)

    @ParameterizedTest(
        name = "When type is {0}, target BankLocal should have {0} as type",
    )
    @EnumSource(TypePath::class)
    fun testConvertToLocal(input: TypePath) {
        val bankResponse =
            BankRemote(
                district = "mock district",
                bankName = "mock bank name",
                typeName = "bank",
                address = "mock address",
                serviceHours = "24 hours",
                latitude = 0.0,
                longitude = 0.0,
            )

        bankDataMapper.convertToLocal(
            language = "en",
            type = input,
            bankRemote = bankResponse,
        ) shouldBe
            BankLocal(
                language = "en",
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
