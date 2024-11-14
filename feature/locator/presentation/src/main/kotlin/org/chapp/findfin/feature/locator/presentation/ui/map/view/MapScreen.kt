package org.chapp.findfin.feature.locator.presentation.ui.map.view

import androidx.compose.runtime.Composable
import org.chapp.findfin.core.map.AppMap
import org.chapp.findfin.core.map.MapMarker
import org.chapp.findfin.core.map.Position
import org.chapp.findfin.core.map.rememberAppMapCameraState

@Composable
internal fun MapScreen() {
    val mapCameraState =
        rememberAppMapCameraState(
            initPosition = Position(latitude = 22.3193, longitude = 114.1694),
            initZoom = 10f,
        )

    AppMap(
        cameraState = mapCameraState,
        markers =
            (1..100).map { index ->
                MapMarker(
                    itemPosition = Position(latitude = 22.3193 + index * 0.001, longitude = 114.1694 + index * 0.001),
                    itemTitle = "Marker $index",
                    itemSnippet = "Snippet $index",
                    itemZIndex = index.toFloat(),
                )
            },
    )
}
