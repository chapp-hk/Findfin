package org.chapp.findfin.feature.locator.presentation.ui.map.viewmodel

import io.kotest.matchers.shouldBe
import org.chapp.findfin.feature.bank.data.repo.location.model.BankLocationModel
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test

@DisplayName("BankLocationModelMapper unit tests")
class BankLocationModelMapperTest {
    @Test
    fun `toMapMarker should map BankLocationModel to MapMarker correctly`() {
        // Arrange
        val model =
            BankLocationModel(
                type = "type",
                district = "district",
                bankName = "bankName",
                typeName = "typeName",
                address = "New York, NY",
                serviceHours = "serviceHours",
                latitude = 40.7128,
                longitude = -74.0060,
            )
        val mapper = BankLocationModelMapper()

        // Act
        val result = mapper.toMapMarker(model)

        // Assert
        // MapMarker extends a class which is hidden from map module
        // so just assert the string representation of the object
        "$result" shouldBe "MapMarker(itemPosition=" +
            "Position(latitude=40.7128, longitude=-74.006), " +
            "itemTitle=New York, NY, " +
            "itemSnippet=, itemZIndex=0.0)"
    }
}
