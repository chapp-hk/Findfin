package org.chapp.findfin.feature.locator.ui.map.view

import androidx.compose.runtime.Composable
import org.chapp.findfin.core.map.AppMap
import org.chapp.findfin.core.map.Position

@Composable
fun MapScreen() {
    AppMap(
        initPosition = Position(22.3193, 114.1694),
        initZoom = 17f,
    )
}
