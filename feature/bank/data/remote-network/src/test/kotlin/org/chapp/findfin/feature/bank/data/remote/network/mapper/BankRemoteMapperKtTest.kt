package org.chapp.findfin.feature.bank.data.remote.network.mapper

import io.kotest.matchers.shouldBe
import org.chapp.findfin.feature.bank.data.remote.network.model.BankResponse
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test

@DisplayName("BankRemoteMapperKtTest")
class BankRemoteMapperKtTest {
    @Test
    fun `toBankRemote() should map BankResponse to BankRemote correctly`() {
        // Given
        val bankResponse =
            BankResponse(
                district = "Test District",
                bankName = "Test Bank",
                typeName = "Test Type",
                address = "Test Address",
                serviceHours = "Test Hours",
                latitude = 1.234567,
                longitude = 2.345678,
            )

        // When
        val result = bankResponse.toBankRemote()

        // Then
        result.district shouldBe bankResponse.district
        result.bankName shouldBe bankResponse.bankName
        result.typeName shouldBe bankResponse.typeName
        result.address shouldBe bankResponse.address
        result.serviceHours shouldBe bankResponse.serviceHours
        result.latitude shouldBe bankResponse.latitude
        result.longitude shouldBe bankResponse.longitude
    }
}
