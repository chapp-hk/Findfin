package org.chapp.findfin.core.map

import android.Manifest
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.ComposeMapColorScheme
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.MapProperties
import com.google.maps.android.compose.MapsComposeExperimentalApi
import com.google.maps.android.compose.clustering.Clustering
import com.google.maps.android.compose.rememberCameraPositionState

/**
 * Displays a Google Map with optional clustering and camera state management.
 *
 * @param modifier The [Modifier] to be applied to the map.
 * @param isMyLocationEnabled Whether to enable the user's current location layer.
 * @param markers The list of [MapMarker]s to display on the map.
 * @param initPosition The initial camera position as a [Position].
 * @param initZoom The initial zoom level for the camera.
 * @param onBoundsChange Callback invoked with the current [PositionBounds]
 * when the camera becomes idle or the map is loaded.
 * @param markerContent Composable function to render the content of each marker.
 */
@OptIn(MapsComposeExperimentalApi::class, ExperimentalPermissionsApi::class)
@Composable
fun <T> AppMap(
    modifier: Modifier = Modifier,
    isMyLocationEnabled: Boolean = false,
    markers: List<MapMarker<T>> = listOf(),
    initPosition: Position,
    initZoom: Float,
    onBoundsChange: (PositionBounds) -> Unit,
    markerContent: @Composable (MapMarker<T>) -> Unit,
) {
    val permissionState = rememberPermissionState(Manifest.permission.ACCESS_FINE_LOCATION)
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
                    LatLng(initPosition.latitude, initPosition.longitude),
                    initZoom,
                )
        }

    val mapColorScheme =
        if (isSystemInDarkTheme()) {
            ComposeMapColorScheme.DARK
        } else {
            ComposeMapColorScheme.LIGHT
        }

    LaunchedEffect(cameraPositionState.isMoving) {
        if (!cameraPositionState.isMoving) {
            cameraPositionState.projection?.visibleRegion?.latLngBounds
                ?.let {
                    PositionBounds(
                        southwest = Position(it.southwest.latitude, it.southwest.longitude),
                        northeast = Position(it.northeast.latitude, it.northeast.longitude),
                    )
                }
                ?.run(onBoundsChange)
        }
    }

    GoogleMap(
        modifier = modifier,
        cameraPositionState = cameraPositionState,
        properties = properties,
        mapColorScheme = mapColorScheme,
        onMapLoaded = {
            cameraPositionState.projection?.visibleRegion?.latLngBounds
                ?.let {
                    PositionBounds(
                        southwest = Position(it.southwest.latitude, it.southwest.longitude),
                        northeast = Position(it.northeast.latitude, it.northeast.longitude),
                    )
                }
                ?.run(onBoundsChange)
        },
    ) {
        Clustering(
            items = markers.map { it.toClusterItem() },
            clusterItemContent = { item ->
                markerContent(item.originalMarker)
            },
        )
    }
}
