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
        val bank = object : BankLocal {
            override val type: String = "some type"
            override val district: String = "somewhere"
            override val bankName: String = "mock bank"
            override val typeName: String = "mock type"
            override val address: String = "some street"
            override val serviceHours: String = "24 hours"
            override val latitude: Double = 22.24214
            override val longitude: Double = 144.5325
        }

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
