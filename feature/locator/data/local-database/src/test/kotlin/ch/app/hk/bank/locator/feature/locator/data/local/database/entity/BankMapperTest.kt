package ch.app.hk.bank.locator.feature.locator.data.local.database.entity

import ch.app.hk.bank.locator.feature.locator.data.local.entity.BankLocal
import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.mapstruct.factory.Mappers


@DisplayName("BankMapper unit tests")
class BankMapperTest {
    private val bankMapper = Mappers.getMapper(BankMapper::class.java)

    @Test
    fun `test clone`() {
        val bank = BankLocal(
            type = "some type",
            district = "somewhere",
            bankName = "mock bank",
            typeName = "mock type",
            address = "some street",
            serviceHours = "24 hours",
            latitude = 22.24214,
            longitude = 144.5325,
        )

        bankMapper.clone(bank) shouldBe
            BankEntity(
                type = "some type",
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
