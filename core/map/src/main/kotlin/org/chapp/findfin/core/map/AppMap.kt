package org.chapp.findfin.core.map

import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.MapProperties
import com.google.maps.android.compose.MapsComposeExperimentalApi
import com.google.maps.android.compose.clustering.Clustering
import com.google.maps.android.compose.rememberCameraPositionState

@OptIn(MapsComposeExperimentalApi::class, ExperimentalPermissionsApi::class)
@Composable
fun AppMap(
    modifier: Modifier = Modifier,
    isMyLocationEnabled: Boolean = false,
    cameraState: AppMapCameraState = rememberAppMapCameraState(),
    markers: List<MapMarker> = listOf(),
    onMapLoaded: () -> Unit = {},
) {
    val permissionState = rememberPermissionState(android.Manifest.permission.ACCESS_FINE_LOCATION)

    val properties by remember {
        derivedStateOf {
            MapProperties(
                isMyLocationEnabled = permissionState.status.isGranted && isMyLocationEnabled,
            )
        }
    }

    val cameraPositionState =
        rememberCameraPositionState {
            position =
                CameraPosition.fromLatLngZoom(
                    LatLng(cameraState.position.latitude, cameraState.position.longitude),
                    cameraState.zoom,
                )
        }

    GoogleMap(
        modifier = modifier,
        cameraPositionState = cameraPositionState,
        properties = properties,
        onMapLoaded = onMapLoaded,
    ) {
        Clustering(items = markers)
    }
}
