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

/**
 * Remembers and saves the state of the map camera position.
 *
 * @param key Optional key to save the state.
 * @param initPosition Initial position of the camera.
 * @param initZoom Initial zoom level of the camera.
 * @return An instance of [AppMapCameraState] with the remembered state.
 */
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

/**
 * State holder for the map camera position and zoom level.
 *
 * @property cameraPositionState The state of the camera position.
 */
@Stable
class AppMapCameraState(
    private val cameraPositionState: CameraPositionState,
) {
    /**
     * The current position of the camera.
     */
    val position: Position
        get() =
            Position(
                cameraPositionState.position.target.latitude,
                cameraPositionState.position.target.longitude,
            )

    /**
     * The current zoom level of the camera.
     */
    val zoom: Float
        get() = cameraPositionState.position.zoom

    /**
     * Moves the camera to the specified position.
     *
     * @param position The new position to move the camera to.
     */
    fun move(position: Position) {
        val update = CameraUpdateFactory.newLatLng(LatLng(position.latitude, position.longitude))
        cameraPositionState.move(update = update)
    }

    /**
     * Animates the camera to the specified position.
     *
     * @param position The new position to animate the camera to.
     * @param durationMs The duration of the animation in milliseconds.
     */
    suspend fun animate(
        position: Position,
        durationMs: Int = Integer.MAX_VALUE,
    ) {
        val update = CameraUpdateFactory.newLatLng(LatLng(position.latitude, position.longitude))
        cameraPositionState.animate(update = update, durationMs = durationMs)
    }

    internal companion object {
        /**
         * A [Saver] implementation to save and restore the [AppMapCameraState].
         */
        val Saver: Saver<AppMapCameraState, CameraPosition> =
            Saver(
                save = { it.cameraPositionState.position },
                restore = { AppMapCameraState(CameraPositionState(it)) },
            )
    }
}
