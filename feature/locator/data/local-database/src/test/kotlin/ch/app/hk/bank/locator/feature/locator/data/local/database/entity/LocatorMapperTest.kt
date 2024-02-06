package ch.app.hk.bank.locator.feature.locator.data.local.database.entity

import ch.app.hk.bank.locator.feature.locator.data.local.entity.LocatorLocal
import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.mapstruct.factory.Mappers

@DisplayName("LocatorMapper unit tests")
class LocatorMapperTest {
    private val locatorMapper = Mappers.getMapper(LocatorMapper::class.java)

    @Test
    fun `test clone`() {
        val locator =
            LocatorLocal(
                type = "some type",
                district = "somewhere",
                bankName = "mock bank",
                typeName = "mock type",
                address = "some street",
                serviceHours = "24 hours",
                latitude = 22.24214,
                longitude = 144.5325,
            )

        locatorMapper.clone(locator) shouldBe
            LocatorEntity(
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
