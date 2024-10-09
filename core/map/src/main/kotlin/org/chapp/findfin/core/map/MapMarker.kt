package org.chapp.findfin.core.map

import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.clustering.ClusterItem

/**
 * Data class representing a marker on the map that can be clustered.
 *
 * @property itemPosition The position of the marker.
 * @property itemTitle The title of the marker.
 * @property itemSnippet The snippet text of the marker.
 * @property itemZIndex The z-index of the marker.
 */
data class MapMarker(
    private val itemPosition: Position,
    private val itemTitle: String,
    private val itemSnippet: String,
    private val itemZIndex: Float,
) : ClusterItem {
    /**
     * Returns the position of the marker as a [LatLng] object.
     */
    override fun getPosition(): LatLng = LatLng(itemPosition.latitude, itemPosition.longitude)

    /**
     * Returns the title of the marker.
     */
    override fun getTitle(): String = itemTitle

    /**
     * Returns the snippet text of the marker.
     */
    override fun getSnippet(): String = itemSnippet

    /**
     * Returns the z-index of the marker.
     */
    override fun getZIndex(): Float = itemZIndex
}
