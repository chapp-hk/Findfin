package org.chapp.findfin.core.map

import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.clustering.ClusterItem

data class MapMarker(
    val position: Position,
    val title: String,
)

data class MapClusterItem(
    val itemPosition: Position,
    val itemTitle: String,
    val itemSnippet: String,
    val itemZIndex: Float,
) : ClusterItem {
    override fun getPosition(): LatLng = LatLng(itemPosition.latitude, itemPosition.longitude)

    override fun getTitle(): String = itemTitle

    override fun getSnippet(): String = itemSnippet

    override fun getZIndex(): Float = itemZIndex
}
