package org.chapp.findfin.feature.locator.presentation.ui.map.model

import io.kotest.matchers.shouldBe
import org.chapp.findfin.core.map.MapMarker
import org.chapp.findfin.core.map.Position
import org.chapp.findfin.feature.bank.data.repo.model.BankModel
import org.chapp.findfin.feature.bank.data.repo.model.BankType
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test

@DisplayName("BankModelMapper unit tests")
class BankModelMapperTest {
    @Test
    fun `toMapMarker should map BankLocationModel to MapMarker correctly`() {
        // Arrange
        val model =
            BankModel(
                type = "ATM",
                district = "district",
                bankName = "bankName",
                typeName = "typeName",
                address = "New York, NY",
                serviceHours = "serviceHours",
                latitude = 40.7128,
                longitude = -74.0060,
            )
        val mapper = BankModelMapper()

        // Act
        val result = mapper.toMapMarker(model)

        // Assert
        result shouldBe
            MapMarker(
                markerPosition = Position(latitude = 40.7128, longitude = -74.0060),
                markerTitle = "New York, NY",
                type = BankType.ATM,
            )
    }
}
