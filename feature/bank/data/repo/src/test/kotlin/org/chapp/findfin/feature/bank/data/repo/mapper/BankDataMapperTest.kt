package org.chapp.findfin.feature.bank.data.repo.mapper

import io.kotest.matchers.shouldBe
import org.chapp.findfin.feature.bank.data.repo.datasource.local.model.BankLocal
import org.chapp.findfin.feature.bank.data.repo.datasource.remote.model.BankRemote
import org.chapp.findfin.feature.bank.data.repo.datasource.remote.model.TypePath
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.CsvFileSource
import org.junit.jupiter.params.provider.EnumSource

@DisplayName("BankDataMapper unit tests")
class BankDataMapperTest {
    @ParameterizedTest(
        name = "When type is {0}, target BankLocal should have {0} as type",
    )
    @EnumSource(TypePath::class)
    fun testBankRemoteToBankLocal(input: TypePath) {
        val bankResponse =
            BankRemote(
                district = "mock area",
                bankName = "mock bank name",
                typeName = "bank",
                address = "mock address",
                serviceHours = "24 hours",
                latitude = 0.0,
                longitude = 0.0,
            )

        bankResponse.toBankLocal(
            language = "en",
            type = input,
        ) shouldBe
            BankLocal(
                language = "en",
                type = input.name,
                district = "mock area",
                bankName = "mock bank name",
                typeName = "bank",
                address = "mock address",
                serviceHours = "24 hours",
                latitude = 0.0,
                longitude = 0.0,
            )
    }

    @ParameterizedTest(
        name = """When input district is "{0}", should map to "{1}" """,
    )
    @CsvFileSource(resources = ["/district.csv"], numLinesToSkip = 1)
    fun `test formatDistrict`(
        input: String,
        expected: String,
    ) {
        val bankLocal =
            BankRemote(
                district = input,
                bankName = "mock bank name",
                typeName = "bank",
                address = "mock address",
                serviceHours = "24 hours",
                latitude = 0.0,
                longitude = 0.0,
            ).toBankLocal(
                language = "en",
                type = TypePath.ATM,
            )

        bankLocal.district shouldBe expected
    }

    @ParameterizedTest(
        name = """When input bank name is "{0}", should map to "{1}" """,
    )
    @CsvFileSource(resources = ["/bank-name.csv"], numLinesToSkip = 1)
    fun `test formatBankName`(
        input: String,
        expected: String,
    ) {
        val bankLocal =
            BankRemote(
                district = "mock area",
                bankName = input,
                typeName = "bank",
                address = "mock address",
                serviceHours = "24 hours",
                latitude = 0.0,
                longitude = 0.0,
            ).toBankLocal(
                language = "en",
                type = TypePath.ATM,
            )

        bankLocal.bankName shouldBe expected
    }
}
