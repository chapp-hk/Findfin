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
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerState
import com.google.maps.android.compose.rememberCameraPositionState

@Composable
fun AppMap(
    modifier: Modifier = Modifier,
    markers: List<MapMarker> = listOf(),
    isMyLocationEnabled: Boolean = false,
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
        onMapLoaded = { },
    ) {
        markers.forEach {
            Marker(
                state = MarkerState(position = LatLng(it.position.latitude, it.position.longitude)),
                title = it.title,
            )
        }
    }
}
