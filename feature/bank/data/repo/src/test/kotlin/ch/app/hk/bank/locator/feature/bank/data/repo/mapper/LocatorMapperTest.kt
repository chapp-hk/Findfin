package ch.app.hk.bank.locator.feature.bank.data.repo.mapper

import ch.app.hk.bank.locator.feature.bank.data.local.model.LocatorLocal
import ch.app.hk.bank.locator.feature.bank.data.remote.api.LocatorPath
import ch.app.hk.bank.locator.feature.bank.data.remote.model.LocatorResponse
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
    @EnumSource(LocatorPath::class)
    fun testConvertToLocal(input: LocatorPath) {
        val locatorResponse =
            LocatorResponse(
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
            locator = locatorResponse,
        ) shouldBe
            LocatorLocal(
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
