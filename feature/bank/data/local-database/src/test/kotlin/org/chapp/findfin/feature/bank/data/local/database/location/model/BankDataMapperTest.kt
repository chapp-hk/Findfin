package org.chapp.findfin.feature.bank.data.local.database.location.model

import io.kotest.matchers.shouldBe
import org.chapp.findfin.feature.bank.data.local.database.model.BankDataMapper
import org.chapp.findfin.feature.bank.data.local.database.model.BankEntity
import org.chapp.findfin.feature.bank.data.repo.local.model.BankLocal
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.mapstruct.factory.Mappers

@DisplayName("LocatorMapper unit tests")
class BankDataMapperTest {
    private val bankDataMapper = Mappers.getMapper(BankDataMapper::class.java)

    @Test
    fun `test clone`() {
        val locator =
            BankLocal(
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

        bankDataMapper.clone(locator) shouldBe
            BankEntity(
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
