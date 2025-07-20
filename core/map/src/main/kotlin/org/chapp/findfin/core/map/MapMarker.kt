package org.chapp.findfin.core.map

import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.clustering.ClusterItem

/**
 * Represents a marker to be displayed on a map.
 *
 * This data class holds all the necessary information to define a map marker,
 * including its geographical position, title, and an optional type parameter
 * to categorize or associate additional data with the marker.
 *
 * @param T The type of the data associated with this marker. This can be used to
 *          store custom information or to differentiate marker types (e.g., bank, ATM, etc.).
 * @property markerPosition The geographical coordinates ([Position]) of the marker on the map.
 * @property markerTitle The text to be displayed as the primary label for the marker,
 *                 often shown when the marker is tapped or hovered over.
 * @property type An optional payload of type [T] that can carry additional domain-specific
 *                information related to this marker.
 */
data class MapMarker<T>(
    val markerPosition: Position,
    val markerTitle: String,
    val type: T,
)

/**
 * Extended ClusterItem interface that includes the original marker type.
 */
interface MapCluster<T> : ClusterItem {
    val originalMarker: MapMarker<T>
}

/**
 * Converts a [MapMarker] to a [ClusterItem] for use in clustering on Google Maps.
 *
 * This function creates an anonymous implementation of the [MapCluster] interface,
 * allowing the marker to be used in clustering algorithms provided by the Google Maps Android API.
 *
 * @return A [MapCluster] representation of this [MapMarker].
 */
internal fun <T> MapMarker<T>.toClusterItem(): MapCluster<T> {
    val marker = this
    return object : MapCluster<T> {
        override fun getPosition(): LatLng = LatLng(marker.markerPosition.latitude, marker.markerPosition.longitude)

        override fun getTitle(): String? = marker.markerTitle

        override fun getSnippet(): String? = null

        override fun getZIndex(): Float? = null

        override val originalMarker: MapMarker<T>
            get() = marker
    }
}

/**
 * Converts a [MapCluster] back to a [MapMarker].
 *
 * @return The original [MapMarker] that was converted to a cluster item.
 */
internal fun <T> MapCluster<T>.toMapMarker(): MapMarker<T> {
    return originalMarker
}
