package org.chapp.findfin.feature.locator.ui.map.view

import androidx.compose.runtime.Composable
import org.chapp.findfin.core.map.AppMap
import org.chapp.findfin.core.map.MapMarker
import org.chapp.findfin.core.map.Position

@Composable
fun MapScreen() {
    AppMap(
        initPosition = Position(22.3193, 114.1694),
        initZoom = 10f,
        markers =
            (1..100).map { index ->
                MapMarker(
                    itemPosition = Position(22.3193 + index * 0.001, 114.1694 + index * 0.001),
                    itemTitle = "Marker $index",
                    itemSnippet = "Snippet $index",
                    itemZIndex = index.toFloat(),
                )
            },
    )
}
