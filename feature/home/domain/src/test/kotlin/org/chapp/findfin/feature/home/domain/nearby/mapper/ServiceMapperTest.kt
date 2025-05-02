package org.chapp.findfin.feature.home.domain.nearby.mapper

import io.kotest.matchers.shouldBe
import org.chapp.findfin.feature.bank.data.repo.model.BankModel
import org.chapp.findfin.feature.home.domain.nearby.model.Service
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.mapstruct.factory.Mappers

@DisplayName("ServiceMapper unit tests")
class ServiceMapperTest {
    private val serviceMapper = Mappers.getMapper(ServiceMapper::class.java)

    @Test
    fun `test clone`() {
        val locator =
            BankModel(
                type = "type",
                district = "district",
                bankName = "bankName",
                typeName = "typeName",
                address = "address",
                serviceHours = "serviceHours",
                latitude = 0.0,
                longitude = 0.0,
            )

        serviceMapper.clone(locator) shouldBe
            Service(
                type = "type",
                district = "district",
                bankName = "bankName",
                typeName = "typeName",
                address = "address",
                serviceHours = "serviceHours",
                latitude = 0.0,
                longitude = 0.0,
            )
    }
}
