package org.chapp.findfin.core.map

import com.google.android.gms.maps.model.LatLng
import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test

class MapMarkerTest {
    // Helper data class for the test payload, can be anything
    data class TestMarkerType(val id: String, val category: String)

    @Test
    fun `toClusterItem should correctly convert MapMarker with String type`() {
        // Arrange
        val testPosition = Position(latitude = 10.0, longitude = 20.0)
        val testTitle = "Test Marker Title"
        val testPayload = "SimpleStringPayload"
        val mapMarker =
            MapMarker(
                markerPosition = testPosition,
                markerTitle = testTitle,
                type = testPayload,
            )

        // Act
        val clusterItem = mapMarker.toClusterItem()

        // Assert
        clusterItem.position shouldBe LatLng(10.0, 20.0)
        clusterItem.title shouldBe "Test Marker Title"
        clusterItem.snippet shouldBe null
        clusterItem.zIndex shouldBe null
    }

    @Test
    fun `toClusterItem should correctly convert MapMarker with custom data class type`() {
        // Arrange
        val testPosition = Position(latitude = -34.05, longitude = 151.20)
        val testTitle = "Sydney Opera House"
        val testPayload = TestMarkerType(id = "SOH123", category = "Landmark")
        val mapMarker =
            MapMarker(
                markerPosition = testPosition,
                markerTitle = testTitle,
                type = testPayload,
            )

        // Act
        val clusterItem = mapMarker.toClusterItem()

        // Assert
        clusterItem.position shouldBe LatLng(-34.05, 151.20)
        clusterItem.title shouldBe "Sydney Opera House"
        clusterItem.snippet shouldBe null
        clusterItem.zIndex shouldBe null
        // You don't directly assert the 'type' in ClusterItem, as it's not part of the interface.
        // The test ensures the other essential ClusterItem properties are correctly mapped.
    }
}
