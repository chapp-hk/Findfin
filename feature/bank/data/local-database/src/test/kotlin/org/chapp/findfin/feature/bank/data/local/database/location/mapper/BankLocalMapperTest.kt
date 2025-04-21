package org.chapp.findfin.feature.bank.data.local.database.location.mapper

import io.kotest.matchers.shouldBe
import org.chapp.findfin.feature.bank.data.local.database.mapper.BankLocalMapper
import org.chapp.findfin.feature.bank.data.local.database.model.BankEntity
import org.chapp.findfin.feature.bank.data.repo.datasource.local.model.BankLocal
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.mapstruct.factory.Mappers

@DisplayName("BankLocalMapper unit tests")
class BankLocalMapperTest {
    private val bankLocalMapper = Mappers.getMapper(BankLocalMapper::class.java)

    @Test
    fun `test toDatabaseEntity`() {
        val bankLocal =
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

        bankLocalMapper.toDatabaseEntity(bankLocal) shouldBe
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

    @Test
    fun `test toLocalModel`() {
        val entity =
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

        bankLocalMapper.toLocalModel(entity) shouldBe
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
    }
}
