package org.chapp.findfin.core.map

import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.clustering.ClusterItem

data class MapMarker(
    private val itemPosition: Position,
    private val itemTitle: String,
    private val itemSnippet: String,
    private val itemZIndex: Float,
) : ClusterItem {
    override fun getPosition(): LatLng = LatLng(itemPosition.latitude, itemPosition.longitude)

    override fun getTitle(): String = itemTitle

    override fun getSnippet(): String = itemSnippet

    override fun getZIndex(): Float = itemZIndex
}
