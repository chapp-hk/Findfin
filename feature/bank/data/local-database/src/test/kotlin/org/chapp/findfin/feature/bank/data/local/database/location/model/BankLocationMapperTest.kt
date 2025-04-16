package org.chapp.findfin.feature.bank.data.local.database.location.model

import io.kotest.matchers.shouldBe
import org.chapp.findfin.feature.bank.data.local.database.model.BankLocationEntity
import org.chapp.findfin.feature.bank.data.local.database.model.BankLocationMapper
import org.chapp.findfin.feature.bank.data.repo.local.model.BankLocationLocal
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.mapstruct.factory.Mappers

@DisplayName("LocatorMapper unit tests")
class BankLocationMapperTest {
    private val bankLocationMapper = Mappers.getMapper(BankLocationMapper::class.java)

    @Test
    fun `test clone`() {
        val locator =
            BankLocationLocal(
                type = "some type",
                language = "en",
                district = "somewhere",
                bankName = "mock bank",
                typeName = "mock type",
                address = "some street",
                serviceHours = "24 hours",
                latitude = 22.24214,
                longitude = 144.5325,
            )

        bankLocationMapper.clone(locator) shouldBe
            BankLocationEntity(
                type = "some type",
                language = "en",
                district = "somewhere",
                bankName = "mock bank",
                typeName = "mock type",
                address = "some street",
                serviceHours = "24 hours",
                latitude = 22.24214,
                longitude = 144.5325,
            )
    }
}
