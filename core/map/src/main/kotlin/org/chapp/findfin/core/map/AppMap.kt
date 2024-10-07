package org.chapp.findfin.core.map

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.MapProperties
import com.google.maps.android.compose.MapsComposeExperimentalApi
import com.google.maps.android.compose.clustering.Clustering
import com.google.maps.android.compose.rememberCameraPositionState

@OptIn(MapsComposeExperimentalApi::class)
@Composable
fun AppMap(
    modifier: Modifier = Modifier,
    markers: List<MapClusterItem> = listOf(),
    isMyLocationEnabled: Boolean = false,
    onMapLoaded: () -> Unit = {},
    initPosition: Position,
    initZoom: Float,
) {
    val cameraPositionState =
        rememberCameraPositionState {
            position =
                CameraPosition.fromLatLngZoom(
                    LatLng(initPosition.latitude, initPosition.longitude),
                    initZoom,
                )
        }

    val properties by remember {
        mutableStateOf(
            MapProperties(
                isMyLocationEnabled = isMyLocationEnabled,
            ),
        )
    }

    GoogleMap(
        modifier = modifier,
        cameraPositionState = cameraPositionState,
        properties = properties,
        onMapLoaded = onMapLoaded,
    ) {
        Clustering(
            items = markers,
            onClusterClick = {
                false
            },
            onClusterItemClick = {
                false
            },
            onClusterItemInfoWindowClick = {
            },
            clusterItemContent = null,
        )
    }
}
