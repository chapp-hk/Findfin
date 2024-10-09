package org.chapp.findfin.core.map

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.saveable.Saver
import androidx.compose.runtime.saveable.rememberSaveable
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.CameraPositionState
import com.google.maps.android.compose.rememberCameraPositionState

@Composable
fun rememberAppMapCameraState(
    key: String? = null,
    initPosition: Position = Position(latitude = 0.0, longitude = 0.0),
    initZoom: Float = 0f,
): AppMapCameraState {
    val cameraPositionState =
        rememberCameraPositionState {
            position =
                CameraPosition.fromLatLngZoom(
                    LatLng(initPosition.latitude, initPosition.longitude),
                    initZoom,
                )
        }

    return rememberSaveable(
        key = key,
        saver = AppMapCameraState.Saver,
    ) {
        AppMapCameraState(
            cameraPositionState = cameraPositionState,
        )
    }
}

@Stable
class AppMapCameraState(
    private val cameraPositionState: CameraPositionState,
) {
    val position: Position
        get() =
            Position(
                cameraPositionState.position.target.latitude,
                cameraPositionState.position.target.longitude,
            )

    val zoom: Float
        get() = cameraPositionState.position.zoom

    fun move(position: Position) {
        val update = CameraUpdateFactory.newLatLng(LatLng(position.latitude, position.longitude))
        cameraPositionState.move(update = update)
    }

    suspend fun animate(
        position: Position,
        durationMs: Int = Integer.MAX_VALUE,
    ) {
        val update = CameraUpdateFactory.newLatLng(LatLng(position.latitude, position.longitude))
        cameraPositionState.animate(update = update, durationMs = durationMs)
    }

    internal companion object {
        val Saver: Saver<AppMapCameraState, CameraPosition> =
            Saver(
                save = { it.cameraPositionState.position },
                restore = { AppMapCameraState(CameraPositionState(it)) },
            )
    }
}
